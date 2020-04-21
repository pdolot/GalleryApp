package com.dolotdev.galleryapp.presentation.page

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dolotdev.galleryapp.data.PhotoRepository
import com.dolotdev.galleryapp.functional.PhotoService

class GalleryViewModel : ViewModel() {

    var photoService: PhotoService? = null
        set(value) {
            field = value
            photoService?.onPictureTaken = {
                photoList.postValue(photoRepository?.fetchPhotos())
            }
        }
    var photoRepository: PhotoRepository? = null
        set(value) {
            field = value
            photoList.postValue(photoRepository?.fetchPhotos())
        }

    var photoList = MutableLiveData<List<Uri>>()

}