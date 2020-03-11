package com.p2lem8dev.esssplash.photos.list.paging

import androidx.paging.DataSource
import com.p2lem8dev.unsplashapi.models.Photo

class PhotosDataSourceFactory(
    private val repository: PhotosPagingRepository
) : DataSource.Factory<Int, Photo>() {

    var dataSource: PhotosDataSource? = null
        private set

    override fun create(): DataSource<Int, Photo> {
        val dataSource = PhotosDataSource(repository)
        this.dataSource = dataSource
        return dataSource
    }
}