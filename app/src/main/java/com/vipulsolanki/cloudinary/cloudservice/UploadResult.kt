package com.vipulsolanki.cloudinary.cloudservice

class UploadResult(val map : Map<Any?, Any?>) {

    val public_id : String by map
    val etag : String by map
    val secure_url : String by map
    val resource_type : String by map
    val format : String by map
    val original_filename : String by map

}