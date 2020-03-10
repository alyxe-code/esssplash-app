package com.p2lem8dev.esssplash.photos

import android.util.Log
import com.p2lem8dev.esssplash.common.list.DataStorage
import com.p2lem8dev.esssplash.photos.PhotosRepository.OrderBy
import com.p2lem8dev.unsplashapi.models.Photo
import com.p2lem8dev.unsplashapi.repository.UnsplashPhotosRepository

class PhotosRepositoryImpl(
    private val storage: DataStorage<Photo>,
    private val repository: UnsplashPhotosRepository,
    private val config: Config
) : PhotosRepository {

    override fun getCached(startPosition: Int, size: Int) = storage.get(startPosition, size)

    override val loadedSize: Int get() = storage.size

    override suspend fun loadInitial() {
        Log.d("PHOTOS", "Loading page 0")
        val items = repository.listPhotos(
            page = config.initPage,
            perPage = 0,
            orderBy = prepareOrderBy(config.orderBy)
        )
        storage.save(items)
    }

    override suspend fun loadPage(page: Int) {
        Log.d("PHOTOS", "Loading page $page")
        val items = repository.listPhotos(
            page = page,
            perPage = config.pageSize,
            orderBy = prepareOrderBy(config.orderBy)
        )
        storage.save(items)
    }

    private fun prepareOrderBy(orderBy: OrderBy) =
        when (orderBy) {
            OrderBy.Latest -> UnsplashPhotosRepository.ListOrderBy.Latest
            OrderBy.Oldest -> UnsplashPhotosRepository.ListOrderBy.Oldest
            OrderBy.Popular -> UnsplashPhotosRepository.ListOrderBy.Popular
        }

    override fun changeListOrderBy(orderBy: OrderBy) {
        storage.clear()
        config.orderBy = orderBy
    }

    class Config(
        val pageSize: Int,
        val initPage: Int = 0,
        var orderBy: OrderBy
    )
}