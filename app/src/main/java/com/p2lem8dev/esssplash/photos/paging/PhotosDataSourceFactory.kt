package com.p2lem8dev.esssplash.photos.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.p2lem8dev.unsplashapi.models.Photo
import com.p2lem8dev.unsplashapi.repository.PhotosRepository
import kotlinx.coroutines.CoroutineScope

class PhotosDataSourceFactory(
    private val repository: PhotosRepository,
    private val scope: CoroutineScope
) : DataSource.Factory<Int, Photo>() {
    private var _dataSource: PhotosDataSource? = null
        set(value) {
            field = value
            (dataSource as MutableLiveData).postValue(value)
        }
    val dataSource: LiveData<PhotosDataSource> = MutableLiveData()

    override fun create(): DataSource<Int, Photo> {
        val repo = PhotosPagedRepositoryImpl(repository)
        val dataSource = PhotosDataSource(
            repository = repo,
            scope = scope
        )
        _dataSource = dataSource
        return dataSource
    }
}