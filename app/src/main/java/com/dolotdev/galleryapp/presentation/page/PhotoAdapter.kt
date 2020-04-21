package com.dolotdev.galleryapp.presentation.page

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dolotdev.galleryapp.R
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoAdapter : RecyclerView.Adapter<PhotoAdapter.VH>() {

    private var items: List<Uri>? = null

    fun setData(data: List<Uri>) {
        this.items = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return VH(view)
    }

    override fun getItemCount() = items?.size ?: 0

    override fun onBindViewHolder(holder: VH, position: Int) {
        val item = items?.get(position)

        holder.itemView.apply {
            Glide.with(context)
                .load(item)
                .into(photo)
        }
    }

    class VH(view: View) : RecyclerView.ViewHolder(view)
}