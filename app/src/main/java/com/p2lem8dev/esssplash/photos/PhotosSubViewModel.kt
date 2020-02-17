package com.p2lem8dev.esssplash.photos

import android.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.p2lem8dev.unsplashapi.models.Photo

class PhotosSubViewModel(
    private val parentViewModel: PhotosViewModel,
    private val photo: Photo
) {
    val username = photo.user.username
    val name = photo.user.name

    val profileImage = photo.user.profileImage.medium
    val imageUrl = photo.urls.regular

    private var _favorite = false
        set(value) {
            field = value
            (favorite as MutableLiveData).postValue(value)
        }
    val favorite: LiveData<Boolean> = MutableLiveData(_favorite)

    fun onClick() = parentViewModel.onImageClicked(photo.id)

    fun onClickFavorite() {
        _favorite = !_favorite
    }
}
