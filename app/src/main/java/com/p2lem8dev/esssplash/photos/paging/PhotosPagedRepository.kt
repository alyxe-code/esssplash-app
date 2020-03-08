package com.p2lem8dev.esssplash.photos.paging

import com.p2lem8dev.unsplashapi.models.Photo

interface PhotosPagedRepository {
    suspend fun loadPage(page: Int, size: Int): List<Photo>
}