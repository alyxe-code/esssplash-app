package com.p2lem8dev.esssplash.photos

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.p2lem8dev.unsplashapi.models.Photo

class PhotosSubViewModel(
    photo: Photo,
    private val parentViewModel: Navigation
) {
    val id = photo.id

    val username = photo.user.username
    val name = photo.user.name

    val profileImage = photo.user.profileImage.medium
    val imageUrl = photo.urls.regular
    val imageColor = Color.parseColor(photo.color)

    private var _isLiked = photo.likedByUser
        private set(value) {
            field = value
            (isLiked as MutableLiveData).postValue(value)
        }
    val isLiked: LiveData<Boolean> = MutableLiveData(_isLiked)

    private var _likes = photo.likes
        set(value) {
            field = value
            (likes as MutableLiveData).postValue(value)
        }
    val likes: LiveData<Int> = MutableLiveData(_likes)

    fun updateLikes(liked: Boolean, likes: Int) {
        _isLiked = liked
        _likes = likes
    }

    fun onClick() = parentViewModel.onItemClicked(id)

    fun onOptionsClicked() = parentViewModel.onItemOptionsClicked(id)

    fun onLikeClicked() = parentViewModel.onItemLikeClicked(this, id, _isLiked)

    fun onCollectClicked() = parentViewModel.onItemCollectClicked(id)

    fun onUserClicked(): Unit = TODO("Not implemented")

    interface Navigation {
        fun onItemClicked(photoId: String)
        fun onItemOptionsClicked(photoId: String)
        fun onItemLikeClicked(self: PhotosSubViewModel, photoId: String, isLiked: Boolean)
        fun onItemCollectClicked(photoId: String)
    }
}
