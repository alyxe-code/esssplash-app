package com.p2lem8dev.unsplashapi.repository

import com.p2lem8dev.unsplashapi.models.FullPhoto
import com.p2lem8dev.unsplashapi.models.Photo
import com.p2lem8dev.unsplashapi.models.PhotoStats

interface PhotosRepository {

    enum class ListOrderBy { Latest, Oldest, Popular }

    suspend fun listPhotos(page: Int, perPage: Int, orderBy: ListOrderBy): List<Photo>

    suspend fun getPhoto(id: String): FullPhoto

    suspend fun getPhotoStats(id: String): PhotoStats

    suspend fun getTrackableDownloadLink(id: String): String

    suspend fun likePhoto(id: String)

    suspend fun unlikePhoto(id: String)
}

