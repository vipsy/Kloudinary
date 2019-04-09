package com.vipulsolanki.cloudinary.model

import androidx.room.*

@Entity(
    tableName = "local_images",
    indices = [Index("uri"), Index("request_id")]
)
data class LocalImage(
    @ColumnInfo(name = "uri") val uri: String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Long = 0

    @ColumnInfo(name = "request_id")
    var requestId : String = ""
}