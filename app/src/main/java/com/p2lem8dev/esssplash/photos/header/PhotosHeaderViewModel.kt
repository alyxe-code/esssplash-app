package com.p2lem8dev.esssplash.photos.header

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.p2lem8dev.unsplashapi.repository.UnsplashPhotosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotosHeaderViewModel(
    private val repository: UnsplashPhotosRepository
) : ViewModel() {

    val searchQuery = MutableLiveData<String>()

    val backgroundUrl: LiveData<String> = MutableLiveData()

    init {
        viewModelScope.launch(Dispatchers.Default) {
            val photo = repository.getRandomPhoto()
            (backgroundUrl as MutableLiveData).postValue(photo.urls.regular)
        }
    }

}