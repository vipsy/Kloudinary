package com.vipulsolanki.kloudinary.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.cloudinary.android.MediaManager
import com.cloudinary.android.ResponsiveUrl
import com.squareup.picasso.Picasso
import com.vipulsolanki.kloudinary.R
import com.vipulsolanki.kloudinary.model.LocalImage
import com.vipulsolanki.kloudinary.model.RemoteImage
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_image.view.*

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    var localImages : List<LocalImage> = emptyList()
    var remoteImages : List<RemoteImage> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false).let {
            ImageViewHolder(it) }
    }

    override fun getItemCount(): Int {
        return localImages.size + remoteImages.size
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        if (position < localImages.size)
            holder.apply(localImages[position])
        else
            holder.apply(remoteImages[position - localImages.size])
    }

    inner class ImageViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun apply(localImage: LocalImage) {
            Picasso.get().load(localImage.uri).into(containerView.image_view)
        }

        fun apply(remoteImage: RemoteImage) {
            MediaManager.get()
                .responsiveUrl(containerView, remoteImage.publicId, ResponsiveUrl.Preset.AUTO_FILL) {
                    Picasso.get().load(remoteImage.secureUrl).into(containerView.image_view)
                }

        }
    }
}