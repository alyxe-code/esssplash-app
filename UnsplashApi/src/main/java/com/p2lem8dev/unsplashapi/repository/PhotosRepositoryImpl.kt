package com.p2lem8dev.unsplashapi.repository

import com.p2lem8dev.unsplashapi.PhotosApi
import com.p2lem8dev.unsplashapi.models.FullPhoto
import com.p2lem8dev.unsplashapi.models.Photo
import com.p2lem8dev.unsplashapi.models.PhotoStats
import java.util.*

internal class PhotosRepositoryImpl(
    private val photosApi: PhotosApi,
    private val appKey: String
) : PhotosRepository {

    override suspend fun listPhotos(
        page: Int,
        perPage: Int,
        orderBy: PhotosRepository.ListOrderBy
    ): List<Photo> = photosApi.listPhotos(
        page = page,
        perPage = perPage,
        orderBy = orderBy.name.toLowerCase(Locale.getDefault()),
        clientId = appKey
    )

    override suspend fun getPhoto(id: String): FullPhoto = photosApi.getPhoto(id, appKey)

    override suspend fun getPhotoStats(id: String): PhotoStats =
        photosApi.getPhotoStatistics(id, clientId = appKey)

    override suspend fun getTrackableDownloadLink(id: String): String =
        photosApi.getPhotoDownloadLink(id, appKey).url

    override suspend fun likePhoto(id: String) {
        photosApi.likePhoto(id, appKey)
    }

    override suspend fun unlikePhoto(id: String) {
        photosApi.unlikePhoto(id, appKey)
    }
}
