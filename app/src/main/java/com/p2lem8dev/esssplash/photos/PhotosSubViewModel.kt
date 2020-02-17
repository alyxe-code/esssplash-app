package com.p2lem8dev.esssplash.photos

import android.graphics.Color
import com.p2lem8dev.unsplashapi.models.Photo

class PhotosSubViewModel(
    private val parentViewModel: PhotosViewModel,
    private val photo: Photo
) {
    val username = photo.user.username
    val name = photo.user.name

    val profileImage = photo.user.profileImage.medium

    val imageUrl = photo.urls.regular
    val imageColor = photo.color.let(Color::parseColor)

    fun onClick() = parentViewModel.onImageClicked(photo.id)

    fun areContentsTheSame(other: PhotosSubViewModel): Boolean {
        return photo.description == other.photo.description
                && photo.altDescription == other.photo.altDescription
                && photo.categories == other.photo.categories
                && photo.links == other.photo.links
                && photo.urls == other.photo.urls
    }

    fun areItemsTheSame(other: PhotosSubViewModel): Boolean =
        photo.id == other.photo.id && photo.user.id == other.photo.user.id
}
