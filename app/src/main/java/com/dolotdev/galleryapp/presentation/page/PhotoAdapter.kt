package com.dolotdev.galleryapp.presentation.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dolotdev.galleryapp.R
import com.dolotdev.galleryapp.data.model.Photo
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoAdapter : PagedListAdapter<Photo, PhotoAdapter.VH>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.apply {
            Glide.with(context)
                .load(getItem(position)?.uri)
                .into(photo)
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