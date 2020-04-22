package com.dolotdev.galleryapp.data

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.dolotdev.galleryapp.constant.Constant
import com.dolotdev.galleryapp.data.model.Photo
import java.io.File

class PhotoRepository(private val context: Context?) {

    fun fetchPhotos(): List<Photo> {
        if (context == null) return emptyList()

        val photosDirectory = File(context.filesDir, "photos")
        return if (photosDirectory.exists()) {
            photosDirectory.listFiles()?.sortedByDescending {
                it.lastModified()
            }?.map {
                Photo(
                    uri = FileProvider.getUriForFile(
                        context,
                        Constant.FILE_PROVIDER_AUTH,
                        it
                    ),
                    name = it.name,
                    lastModified = it.lastModified()
                )
            } ?: emptyList()
        } else {
            emptyList()
        }
    }
}