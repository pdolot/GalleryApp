package com.dolotdev.galleryapp.data.model

import android.net.Uri

data class Photo(
    val uri: Uri,
    val name: String,
    val lastModified: Long
)