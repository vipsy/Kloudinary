package com.vipulsolanki.kloudinary.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.vipulsolanki.kloudinary.model.RemoteImage

@Dao
interface ImagesDao {
    @Query("SELECT * FROM remote_images ORDER BY id DESC")
    fun getRemoteImages(): LiveData<List<RemoteImage>>

    @Query("SELECT * FROM remote_images WHERE public_id = :publicId")
    fun getRemoteImage(publicId: String): RemoteImage

    @Query("SELECT * FROM remote_images WHERE etag = :etag")
    fun getRemoteImageWithEtag(etag: String): RemoteImage?

    @Insert
    fun insertRemoteImage(image: RemoteImage): Long

    @Delete
    fun deleteRemoteImage(image: RemoteImage)


    //For local images
    @Query("SELECT * FROM local_images")
    fun getLocalImages(): LiveData<List<LocalImage>>

    @Query("SELECT * FROM local_images WHERE request_id = :requestId")
    fun getLocalImageWithRequestId(requestId: String): LocalImage?

    @Insert
    fun insertLocalImage(image: LocalImage): Long

    @Delete
    fun deleteLocalImage(image: LocalImage)
}