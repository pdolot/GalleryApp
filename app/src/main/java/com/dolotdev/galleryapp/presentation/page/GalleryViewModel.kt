package com.dolotdev.galleryapp.presentation.page

import androidx.lifecycle.ViewModel
import com.dolotdev.galleryapp.functional.PhotoService

class GalleryViewModel : ViewModel() {
    var photoService: PhotoService? = null
}