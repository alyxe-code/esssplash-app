package com.p2lem8dev.esssplash.photos.paging

import android.util.Log
import androidx.paging.PositionalDataSource
import com.p2lem8dev.esssplash.photos.PhotosRepository
import com.p2lem8dev.unsplashapi.models.Photo

class PhotosDataSource(
    private val repository: PhotosRepository
) : PositionalDataSource<Photo>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Photo>) {
        Log.d(
            "PHOTOS",
            "DataSource load initial ${params.requestedStartPosition} ${repository.loadedSize}"
        )
        callback.onResult(repository.getCached(0, repository.loadedSize), 0)
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Photo>) {
        Log.d("PHOTOS", "DataSource load range ${params.startPosition} ${params.loadSize}")
        callback.onResult(repository.getCached(params.startPosition, params.loadSize))
    }
}