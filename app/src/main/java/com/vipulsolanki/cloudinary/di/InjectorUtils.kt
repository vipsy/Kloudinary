package com.vipulsolanki.cloudinary.di

import android.content.Context
import com.vipulsolanki.cloudinary.model.AppDatabase
import com.vipulsolanki.cloudinary.model.ImagesRepository
import com.vipulsolanki.cloudinary.viewmodel.ImagesViewModelFactory

object InjectorUtils {

    fun provideImagesViewModelFactory(
        context: Context
    ): ImagesViewModelFactory {
        return ImagesViewModelFactory(getImagesRepository(context))
    }

    private fun getImagesRepository(context: Context): ImagesRepository {
        return ImagesRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).imagesDao()
        )
    }
}