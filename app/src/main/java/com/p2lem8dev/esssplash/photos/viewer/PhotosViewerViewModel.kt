package com.p2lem8dev.esssplash.photos.viewer

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.p2lem8dev.unsplashapi.repository.PhotosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotosViewerViewModel(private val repository: PhotosRepository) : ViewModel() {

    private var _properties: Properties? = null
        private set(value) {
            field = value
            (properties as MutableLiveData).postValue(value)
        }
    val properties: LiveData<Properties> = MutableLiveData<Properties>(_properties)

    class Properties(
        val profileImage: String,
        val fullName: String,
        val username: String,
        val mainImage: String,
        val mainImageColor: Int,
        val description: String?
    )

    val isFavorite: LiveData<Boolean> = MutableLiveData<Boolean>(false)

    fun init(photoId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val photo = repository.getPhoto(photoId)
            _properties = Properties(
                profileImage = photo.user.profileImage.medium,
                username = photo.user.username,
                description = photo.description.takeIf { !it.isNullOrBlank() }
                    ?: photo.altDescription.takeIf { !it.isNullOrBlank() },
                fullName = photo.user.name ?: photo.user.username,
                mainImage = photo.urls.regular,
                mainImageColor = Color.parseColor(photo.color)
            ).also {
                Gson().toJson(it).let { Log.d("VIEWER", it) }
            }
        }
    }

    fun onClickFavorite() = Unit

    fun onClickCollect() = Unit

    fun onClickShare() = Unit

    fun onClickDownload() = Unit

}
