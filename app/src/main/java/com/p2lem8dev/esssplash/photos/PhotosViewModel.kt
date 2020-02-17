package com.p2lem8dev.esssplash.photos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.p2lem8dev.esssplash.common.livenavigation.LiveNavigation
import com.p2lem8dev.esssplash.common.livenavigation.LiveNavigationImplementation
import com.p2lem8dev.unsplashapi.repository.PhotosRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotosViewModel(private val repository: PhotosRepository) : ViewModel() {

    private var _parts = mutableListOf<PhotosSubViewModel>()
        private set(value) {
            field = value
            (parts as MutableLiveData).postValue(value)
        }
    val parts: LiveData<List<PhotosSubViewModel>> = MutableLiveData<List<PhotosSubViewModel>>()

    private val _navigation = LiveNavigationImplementation<Navigation>()
    val navigation: LiveNavigation<Navigation> = _navigation

    private var currentPage = -1
    private var loadingOrder = PhotosRepository.ListOrderBy.Latest

    init {
        loadNext()
    }

    fun loadNext() {
        viewModelScope.launch(Dispatchers.IO) {
            val page = currentPage + 1
            val photos = try {
                repository.listPhotos(page, 10, loadingOrder)
            } catch (t: Throwable) {
                null
            }

            Log.d("PHOTOS", "Loaded ${photos?.size ?: 0} photos")
            photos ?: return@launch

            val newList = photos.map { photo ->
                PhotosSubViewModel(
                    this@PhotosViewModel,
                    photo
                )
            }
            _parts = (_parts + newList) as MutableList<PhotosSubViewModel>

            currentPage = page
        }
    }

    fun onImageClicked(imageId: String) = Unit

    interface Navigation
}
