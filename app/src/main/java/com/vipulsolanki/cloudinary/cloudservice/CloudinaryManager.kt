package com.vipulsolanki.cloudinary.cloudservice

import android.content.Context
import com.cloudinary.android.MediaManager
import com.cloudinary.android.policy.GlobalUploadPolicy
import com.cloudinary.android.policy.UploadPolicy

class CloudinaryManager {

    companion object {

        fun init(context : Context) {
            val config = mapOf("cloud_name" to "vipsy",
                "api_key" to "888868235165542",
                "api_secret" to "oaiym0yAGA3p3eJgGplZWz84EA0")

            MediaManager.init(context, config)
            GlobalUploadPolicy.Builder()
                .maxConcurrentRequests(3)
                .maxRetries(5)
                .backoffCriteria(20*1000, UploadPolicy.BackoffPolicy.EXPONENTIAL)
                .build().let {
                    MediaManager.get().globalUploadPolicy = it
                }
        }

    }


}