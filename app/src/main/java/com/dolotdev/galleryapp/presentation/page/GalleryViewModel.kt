package com.dolotdev.galleryapp.presentation.page

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.Config
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.dolotdev.galleryapp.data.PhotoRepository
import com.dolotdev.galleryapp.data.dataSource.PhotoDataSourceFactory
import com.dolotdev.galleryapp.data.model.Photo
import com.dolotdev.galleryapp.functional.PhotoService

class GalleryViewModel : ViewModel() {

    var photoService: PhotoService? = null
    var photoRepository: PhotoRepository? = null
        set(value) {
            field = value
            field?.let { initFactory(it) }
        }

    lateinit var photoDataSourceFactory: PhotoDataSourceFactory
    lateinit var photos: LiveData<PagedList<Photo>>

    private fun initFactory(photoRepository: PhotoRepository) {
        photoDataSourceFactory = PhotoDataSourceFactory(photoRepository)
        photos = photoDataSourceFactory.toLiveData(
            Config(
                pageSize = 8,
                initialLoadSizeHint = 16,
                enablePlaceholders = false
            )
        )

        photoService?.onPictureTaken = {
            photoDataSourceFactory.latestSource.invalidate()
        }
    }


}