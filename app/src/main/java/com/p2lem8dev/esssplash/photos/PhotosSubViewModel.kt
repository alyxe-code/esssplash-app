package com.p2lem8dev.esssplash.photos

import android.graphics.Color
import com.p2lem8dev.esssplash.photos.models.Photo

class PhotosSubViewModel(
    private val parentViewModel: PhotosViewModel,
    val photo: Photo?
) {

    val imageUrl = photo?.urls?.regular
    val placeholderColor = photo?.color ?: Color.WHITE

    fun areContentsTheSame(other: PhotosSubViewModel): Boolean {
        return photo?.description == other.photo?.description
                && photo?.description2 == other.photo?.description2
                && photo?.categories == other.photo?.categories
                && photo?.links == other.photo?.links
                && photo?.urls == other.photo?.urls
    }

    fun areItemsTheSame(other: PhotosSubViewModel): Boolean =
        photo?.id == other.photo?.id && photo?.user?.id == other.photo?.user?.id
}
