package com.p2lem8dev.esssplash.photos.paging

import com.p2lem8dev.unsplashapi.models.Photo

interface PhotosPagingRepository {

    enum class OrderBy { Latest, Oldest, Popular }

    fun changeListOrderBy(orderBy: OrderBy)

    val loadedSize: Int

    fun getCached(startPosition: Int, size: Int): List<Photo>

    suspend fun loadInitial()
    suspend fun loadPage(page: Int)
}