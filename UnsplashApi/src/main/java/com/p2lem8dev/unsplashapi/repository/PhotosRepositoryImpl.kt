package com.p2lem8dev.unsplashapi.repository

import com.p2lem8dev.unsplashapi.PhotosApi
import com.p2lem8dev.unsplashapi.models.FullPhoto
import com.p2lem8dev.unsplashapi.models.Photo
import com.p2lem8dev.unsplashapi.models.PhotoStats
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

internal class PhotosRepositoryImpl(
    private val photosApi: PhotosApi,
    private val appKey: String
) : UnsplashPhotosRepository {

    override suspend fun listPhotos(
        page: Int,
        perPage: Int,
        orderBy: UnsplashPhotosRepository.ListOrderBy
    ): List<Photo> = withContext(Dispatchers.IO) {
        photosApi.listPhotos(
            page = page,
            perPage = perPage,
            orderBy = orderBy.name.toLowerCase(Locale.getDefault()),
            clientId = appKey
        )
    }

    override suspend fun getPhoto(id: String): FullPhoto =
        withContext(Dispatchers.IO) { photosApi.getPhoto(id, appKey) }

    override suspend fun getPhotoStats(id: String): PhotoStats =
        withContext(Dispatchers.IO) { photosApi.getPhotoStatistics(id, clientId = appKey) }

    override suspend fun getTrackableDownloadLink(id: String): String =
        withContext(Dispatchers.IO) { photosApi.getPhotoDownloadLink(id, appKey).url }

    override suspend fun likePhoto(id: String): Photo = withContext(Dispatchers.IO) {
        photosApi
            .likePhoto(id, appKey)
            .photo
    }

    override suspend fun unlikePhoto(id: String): Photo = withContext(Dispatchers.IO) {
        photosApi
            .unlikePhoto(id, appKey)
            .photo
    }

    override suspend fun getRandomPhoto(): Photo =
        withContext(Dispatchers.IO) { photosApi.getRandomPhoto() }
}
