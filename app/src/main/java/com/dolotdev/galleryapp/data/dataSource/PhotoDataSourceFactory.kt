package com.dolotdev.galleryapp.data.dataSource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dolotdev.galleryapp.data.PhotoRepository
import com.dolotdev.galleryapp.data.model.Photo

class PhotoDataSourceFactory(private val photoRepository: PhotoRepository) :
    DataSource.Factory<Long, Photo>() {

    val sourceLiveData = MutableLiveData<PhotoDataSource>()
    lateinit var latestSource: PhotoDataSource

    override fun create(): DataSource<Long, Photo> {
        latestSource = PhotoDataSource(photoRepository)
        sourceLiveData.postValue(latestSource)
        return latestSource
    }
}