package com.vipulsolanki.cloudinary.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vipulsolanki.cloudinary.model.ImagesRepository

class ImagesViewModelFactory(
    private val repository: ImagesRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ImagesViewModel(repository) as T
    }
}