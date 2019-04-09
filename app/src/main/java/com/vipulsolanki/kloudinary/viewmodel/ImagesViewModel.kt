package com.vipulsolanki.kloudinary.viewmodel

import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.vipulsolanki.kloudinary.cloudservice.UploadResult
import com.vipulsolanki.kloudinary.model.ImagesRepository
import com.vipulsolanki.kloudinary.model.LocalImage
import com.vipulsolanki.kloudinary.model.RemoteImage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val TAG = "TAG"

class ImagesViewModel(val imagesRepository: ImagesRepository) : ViewModel() {

    val remoteImages = imagesRepository.getRemoteImages()
    val localImages = imagesRepository.getLocalImages()

    var hostCallback: ImagesViewModelHostCallback? = null

    fun startImageUpload(uri: Uri, md5: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (null == imagesRepository.getRemoteImageWithEtag(md5)) {
                    addLocalImage(uri);
                } else {
                    Log.d(TAG, "Image already uploaded")
                    hostCallback?.let { it.notifyImageExist() }
                }
            }
        }
    }

    fun addLocalImage(uri : Uri) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                var reqId = MediaManager.get().upload(uri).callback(callback).dispatch()
                LocalImage(uri.toString()).apply {
                    requestId = reqId
                    imagesRepository.insertLocalImage(this);
                }
            }
        }
    }

    fun addRemoteImage(result : UploadResult) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                imagesRepository.insertRemoteImage(RemoteImage.from(result))
            }
        }
    }

    fun deleteLocalImageWithReqId(reqId: String) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                imagesRepository.deleteLocalImageWithReqId(reqId)
            }
        }
    }

    val callback = object : UploadCallback {
        override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
            Log.d(TAG, "onSuccess reqId=$requestId  $resultData")
            resultData?.toMap().let {map ->
                map?.let {
                    UploadResult(it).apply {
                        addRemoteImage(this)
                        requestId?.let {deleteLocalImageWithReqId(it)}
                        hostCallback?.let { it.notifyImageUploadSuccess() }
                    }
                }
            }
        }

        override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
            Log.d(TAG, "OnProgress: reqId=$requestId    $bytes out of $totalBytes uploaded")
        }

        override fun onReschedule(requestId: String?, error: ErrorInfo?) {
            Log.d(TAG, "onReschedule: reqId=$requestId    $error")
        }

        override fun onError(requestId: String?, error: ErrorInfo?) {
            Log.d(TAG, "onError: reqId=$requestId    $error")
            hostCallback?.let { it.notifyImageUploadFailed() }
        }

        override fun onStart(requestId: String?) {
            Log.d(TAG, "onStart: reqId=$requestId")
        }

    }
}