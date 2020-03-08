package com.p2lem8dev.esssplash.photos.paging

import com.p2lem8dev.unsplashapi.models.Photo
import com.p2lem8dev.unsplashapi.repository.PhotosRepository

class PhotosPagedRepositoryImpl(private val repository: PhotosRepository) :
    PhotosPagedRepository {

    private var orderBy = PhotosRepository.ListOrderBy.Popular

    override suspend fun loadPage(page: Int, size: Int): List<Photo> = repository.listPhotos(
        page = page,
        orderBy = orderBy,
        perPage = size
    )
}