package com.vipulsolanki.cloudinary.viewmodel

interface ImagesViewModelHostCallback {

    fun notifyImageExist()
    fun notifyImageUploadFailed()
    fun notifyImageUploadSuccess()
}