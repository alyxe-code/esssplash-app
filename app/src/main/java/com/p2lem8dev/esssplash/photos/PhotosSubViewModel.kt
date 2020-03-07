package com.p2lem8dev.esssplash.photos

import com.p2lem8dev.unsplashapi.models.Photo

class PhotosSubViewModel(
    private val parentViewModel: Navigation,
    private val photo: Photo
) {
    val username = photo.user.username
    val name = photo.user.name

    val profileImage = photo.user.profileImage.medium
    val imageUrl = photo.urls.regular

    fun onClick() = parentViewModel.onItemClicked(photo.id)

    fun onOptionsClicked() = parentViewModel.onItemOptionsClicked(photo.id)

    interface Navigation {
        fun onItemClicked(photoId: String)
        fun onItemOptionsClicked(photoId: String)
    }
}
