package com.p2lem8dev.esssplash.photos

import com.p2lem8dev.esssplash.photos.models.Photo

interface PhotosRepository {
    suspend fun getPhotos(page: Int, orderBy: OrderBy): List<Photo>

    enum class OrderBy(val key: String) {
        Popular("popular"),
        Latest("latest"),
        Oldest("oldest")
    }
}
