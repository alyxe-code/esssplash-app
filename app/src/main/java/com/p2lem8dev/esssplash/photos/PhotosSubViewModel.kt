package com.p2lem8dev.esssplash.photos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.p2lem8dev.unsplashapi.models.Photo

class PhotosSubViewModel(
    private val parentViewModel: Navigation,
    val photo: Photo
) {
    val username = photo.user.username
    val name = photo.user.name

    val profileImage = photo.user.profileImage.medium
    val imageUrl = photo.urls.regular

    var likedByUser = photo.likedByUser
        set(value) {
            field = value
            (isLiked as MutableLiveData).postValue(value)
        }
    val isLiked: LiveData<Boolean> = MutableLiveData(likedByUser)

    fun onClick() = parentViewModel.onItemClicked(photo.id)

    fun onOptionsClicked() = parentViewModel.onItemOptionsClicked(photo.id)

    fun onLikeClicked() = parentViewModel.onItemLikeClicked(photo.id)

    fun onCollectClicked() = parentViewModel.onItemCollectClicked(photo.id)

    interface Navigation {
        fun onItemClicked(photoId: String)
        fun onItemOptionsClicked(photoId: String)
        fun onItemLikeClicked(photoId: String)
        fun onItemCollectClicked(photoId: String)
    }
}
