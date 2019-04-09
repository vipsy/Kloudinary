package com.vipulsolanki.kloudinary.viewmodel

interface ImagesViewModelHostCallback {

    fun notifyImageExist()
    fun notifyImageUploadFailed()
    fun notifyImageUploadSuccess()
}