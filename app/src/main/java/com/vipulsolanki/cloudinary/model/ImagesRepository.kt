package com.vipulsolanki.cloudinary.model

import androidx.lifecycle.LiveData

class ImagesRepository(val imagesDao : ImagesDao) {

    fun getRemoteImages() : LiveData<List<RemoteImage>> {
        return imagesDao.getRemoteImages()
    }

    fun insertRemoteImage(image: RemoteImage) : Long {
        return imagesDao.insertRemoteImage(image)
    }

    fun getRemoteImageWithEtag(etag: String): RemoteImage? {
        return imagesDao.getRemoteImageWithEtag(etag);
    }

    fun getLocalImages() : LiveData<List<LocalImage>> {
        return imagesDao.getLocalImages()
    }

    fun insertLocalImage(image: LocalImage) : Long {
        return imagesDao.insertLocalImage(image)
    }

    fun deleteLocalImageWithReqId(reqId: String) {
        imagesDao.getLocalImageWithRequestId(reqId)?.let {
            imagesDao.deleteLocalImage(it)
        }
    }

    fun deleteLocalImage(image : LocalImage) {
        return imagesDao.deleteLocalImage(image);
    }

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: ImagesRepository? = null

        fun getInstance(imagesDao: ImagesDao) =
            instance ?: synchronized(this) {
                instance
                    ?: ImagesRepository(imagesDao).also { instance = it }
            }
    }
}