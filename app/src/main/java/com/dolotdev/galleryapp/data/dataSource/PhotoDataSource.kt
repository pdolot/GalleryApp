package com.dolotdev.galleryapp.data.dataSource

import androidx.paging.ItemKeyedDataSource
import com.dolotdev.galleryapp.data.PhotoRepository
import com.dolotdev.galleryapp.data.model.Photo

class PhotoDataSource(photoRepository: PhotoRepository) :
    ItemKeyedDataSource<Long, Photo>() {

    private val photos = photoRepository.fetchPhotos()

    private fun loadInternal(key: Long?, loadSize: Int): List<Photo> {
        return if (key == null) {
            photos.take(loadSize)
        } else {
            photos.filter {
                it.lastModified < key
            }
        }
    }

    override fun loadInitial(
        params: LoadInitialParams<Long>,
        callback: LoadInitialCallback<Photo>
    ) {
        callback.onResult(loadInternal(null, params.requestedLoadSize))
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<Photo>) {
        callback.onResult(loadInternal(params.key, params.requestedLoadSize))
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<Photo>) {
        // Ignore
    }

    override fun getKey(item: Photo): Long = item.lastModified
}