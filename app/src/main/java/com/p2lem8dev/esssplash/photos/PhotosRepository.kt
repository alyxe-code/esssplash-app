package com.p2lem8dev.esssplash.photos

import com.p2lem8dev.esssplash.photos.models.Photo

interface PhotosRepository {
    fun getPhotos(page: Int, orderBy: OrderBy): List<Photo>

    enum class OrderBy { Popular, Latest, Oldest }
}
