package com.vipulsolanki.cloudinary

import android.app.Application
import com.vipulsolanki.cloudinary.cloudservice.CloudinaryManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        CloudinaryManager.init(this)
    }
}