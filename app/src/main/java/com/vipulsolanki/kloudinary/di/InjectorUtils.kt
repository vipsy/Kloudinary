package com.vipulsolanki.kloudinary.di

import android.content.Context
import com.vipulsolanki.kloudinary.model.AppDatabase
import com.vipulsolanki.kloudinary.model.ImagesRepository
import com.vipulsolanki.kloudinary.viewmodel.ImagesViewModelFactory

object InjectorUtils {

    fun provideImagesViewModelFactory(
        context: Context
    ): ImagesViewModelFactory {
        return ImagesViewModelFactory(
            getImagesRepository(
                context
            )
        )
    }

    private fun getImagesRepository(context: Context): ImagesRepository {
        return ImagesRepository.getInstance(
            AppDatabase.getInstance(context.applicationContext).imagesDao()
        )
    }
}