package com.dolotdev.galleryapp.presentation.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.dolotdev.galleryapp.R
import com.dolotdev.galleryapp.data.model.Photo
import kotlinx.android.synthetic.main.item_photo.view.*
import java.text.SimpleDateFormat
import java.util.*

class PhotoAdapter : PagedListAdapter<Photo, PhotoAdapter.VH>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.apply {
            val item = getItem(position)
            Glide.with(context)
                .load(item?.uri)
                .transform(CenterCrop(), RoundedCorners(48))
                .placeholder(R.drawable.view_image_placeholder)
                .into(photo)

            fileName.text = item?.name
            modifyDate.text = SimpleDateFormat(
                "MM/dd/yyyy HH:mm:ss",
                Locale.getDefault()
            ).format(Date(item?.lastModified ?: 0))
        }
    }

    companion object {
        private val DIFF_CALLBACK = object :
            DiffUtil.ItemCallback<Photo>() {
            override fun areItemsTheSame(
                oldPhoto: Photo,
                newPhoto: Photo
            ) = oldPhoto === newPhoto

            override fun areContentsTheSame(
                oldPhoto: Photo,
                newPhoto: Photo
            ) = oldPhoto == newPhoto
        }
    }

    class VH(view: View) : RecyclerView.ViewHolder(view)
}