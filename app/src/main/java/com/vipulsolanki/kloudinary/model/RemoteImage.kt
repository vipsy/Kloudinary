package com.vipulsolanki.kloudinary.model

import androidx.room.*
import com.vipulsolanki.kloudinary.cloudservice.UploadResult

/***
 * RemoteImage represents an already uploaded image to cloudinary.
 * I couldn't find a way to browse existing images and get all public-Ids,
 * and don't have a server component where I can keep track of all images
 * so created this class to keep local track of all images uploaded.
 *
 */

@Entity(
    tableName = "remote_images",
    indices = [Index("public_id"), Index("etag")]
)
data class RemoteImage(
    @ColumnInfo(name = "public_id") val publicId: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var imageId: Long = 0

    @ColumnInfo(name = "etag")
    var etag: String = ""

    @ColumnInfo(name = "secure_url")
    var secureUrl: String = ""

    @ColumnInfo(name = "resource_type")
    var resourceType: String = ""

    @ColumnInfo(name = "format")
    var format: String = ""

    @ColumnInfo(name = "original_filename")
    var originalFilename: String = ""

    companion object {
        fun from(result: UploadResult): RemoteImage {
            return RemoteImage(result.public_id).apply {
                etag = result.etag
                secureUrl = result.secure_url
                resourceType = result.resource_type
                format = result.format
                originalFilename = result.original_filename
            }
        }
    }
}