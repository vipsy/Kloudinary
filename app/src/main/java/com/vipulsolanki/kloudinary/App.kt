package com.vipulsolanki.kloudinary

import android.app.Application
import com.vipulsolanki.kloudinary.cloudservice.CloudinaryManager

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        CloudinaryManager.init(this)
    }
}