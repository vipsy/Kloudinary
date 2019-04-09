package com.vipulsolanki.cloudinary.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vipulsolanki.cloudinary.model.ImagesDao
import com.vipulsolanki.cloudinary.model.RemoteImage

const val DATABASE_NAME = "app-db"

@Database(entities = [LocalImage::class, RemoteImage::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun imagesDao(): ImagesDao

    companion object {

        // For Singleton instantiation
        @Volatile private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        // Create and pre-populate the database.
        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)
                .addCallback(object : RoomDatabase.Callback() {
                    //If you want to pre-populate with anything
                })
                .build()
        }
    }
}
