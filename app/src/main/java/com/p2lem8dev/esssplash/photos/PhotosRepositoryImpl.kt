package com.p2lem8dev.esssplash.photos

import android.graphics.Color
import com.p2lem8dev.esssplash.app.Constants
import com.p2lem8dev.esssplash.photos.models.Photo
import com.p2lem8dev.esssplash.photos.models.User

class PhotosRepositoryImpl(private val api: PhotosApi) : PhotosRepository {

    override suspend fun getPhotos(page: Int, orderBy: PhotosRepository.OrderBy): List<Photo> {
        val photos = api.loadPage(
            page = page,
            perPage = Constants.PHOTOS_PER_PAGE,
            orderBy = orderBy.key,
            clientId = Constants.ACCESS_KEY
        )
        return photos.map {
            Photo(
                id = it.id,
                color = Color.parseColor(it.color),
                description = it.description,
                categories = it.categories,
                created = it.created,
                currentUserCollections = it.currentUserCollections,
                description2 = it.altDescription,
                height = it.height,
                likedByUser = it.likedByUser,
                likes = it.likes,
                links = Photo.Links(
                    self = it.links.self,
                    download = it.links.download,
                    downloadLocation = it.links.downloadLocation,
                    html = it.links.html
                ),
                updated = it.updated,
                urls = Photo.Urls(
                    raw = it.urls.raw,
                    full = it.urls.full,
                    regular = it.urls.regular,
                    small = it.urls.small,
                    thumb = it.urls.thumb
                ),
                user = User(
                    id = it.user.id,
                    updated = it.user.updated,
                    links = User.Links(
                        html = it.user.links.html,
                        portfolio = it.user.links.portfolio,
                        likes = it.user.links.likes,
                        followers = it.user.links.followers,
                        following = it.user.links.following,
                        photos = it.user.links.photos,
                        self = it.user.links.self
                    ),
                    acceptedTos = it.user.acceptedTos,
                    bio = it.user.bio,
                    firstName = it.user.firstName,
                    instagram = it.user.instagram,
                    lastName = it.user.lastName,
                    location = it.user.location,
                    name = it.user.name,
                    portfolio = it.user.portfolio,
                    profileImage = it.user.profileImage?.let {
                        User.ProfileImage(
                            small = it.small,
                            medium = it.medium,
                            large = it.large
                        )
                    },
                    totalCollections = it.user.totalCollections,
                    totalLikes = it.user.totalLikes,
                    totalPhotos = it.user.totalPhotos,
                    twitterUsername = it.user.twitterUsername,
                    username = it.user.username
                ),
                width = it.width
            )
        }
    }

}
