package com.vipulsolanki.cloudinary.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.vipulsolanki.cloudinary.R
import com.vipulsolanki.cloudinary.di.InjectorUtils
import com.vipulsolanki.cloudinary.utils.MessageDigestUtil
import com.vipulsolanki.cloudinary.viewmodel.ImagesViewModel
import com.vipulsolanki.cloudinary.viewmodel.ImagesViewModelHostCallback
import kotlinx.android.synthetic.main.activity_main.*

const val REQUEST_PICK_IMAGES = 1001

class MainActivity : AppCompatActivity(), ImagesViewModelHostCallback {
    lateinit var imagesViewModel: ImagesViewModel
    lateinit var imagesAdapter : ImagesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imagesViewModel = ViewModelProviders.of(this, InjectorUtils.provideImagesViewModelFactory(this)).get(
            ImagesViewModel::class.java)
        imagesViewModel.hostCallback = this

        imagesAdapter = ImagesAdapter()

        recyclerView.adapter = imagesAdapter
        recyclerView.layoutManager = StaggeredGridLayoutManager(2, RecyclerView.VERTICAL)

        imagesViewModel.localImages.observeForever {
                localImages -> imagesAdapter.localImages = localImages
            imagesAdapter.notifyDataSetChanged()
        }
        imagesViewModel.remoteImages.observeForever {
                remoteImages -> imagesAdapter.remoteImages = remoteImages
            imagesAdapter.notifyDataSetChanged()
        }

        fab.setOnClickListener {
            pickImages()
        }
    }

    private fun pickImages() {
        Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "image/*"
            addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(this, REQUEST_PICK_IMAGES)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_PICK_IMAGES -> {
                if (resultCode == Activity.RESULT_OK) {
                    data?.data?.also { uri ->
                        imagesViewModel.startImageUpload(uri, MessageDigestUtil.md5(contentResolver.openInputStream(uri)))
                    }
                }
            }
        }

    }

    override fun notifyImageExist() {
        Snackbar.make(recyclerView, "Image already exists (ㆆ_ㆆ)", Snackbar.LENGTH_LONG).show();
    }

    override fun notifyImageUploadFailed() {
        Snackbar.make(recyclerView, "Image upload failed ( ˘︹˘ )", Snackbar.LENGTH_LONG).show();
    }

    override fun notifyImageUploadSuccess() {
        Snackbar.make(recyclerView, "Image uploaded  \uD83D\uDC4D", Snackbar.LENGTH_LONG).show();
    }
}
