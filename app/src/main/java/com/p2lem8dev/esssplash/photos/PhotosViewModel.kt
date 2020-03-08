package com.p2lem8dev.esssplash.photos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.p2lem8dev.esssplash.common.livenavigation.LiveNavigation
import com.p2lem8dev.esssplash.common.livenavigation.LiveNavigationCoroutineImplementation
import com.p2lem8dev.unsplashapi.repository.PhotosRepository
import kotlinx.coroutines.*

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class PhotosViewModel(private val repository: PhotosRepository) : ViewModel(),
    PhotosSubViewModel.Navigation {

    private var _photos = mutableListOf<PhotosSubViewModel>()
        private set(value) {
            field = value
            (photos as MutableLiveData).postValue(value)
        }
    val photos: LiveData<List<PhotosSubViewModel>> = MutableLiveData<List<PhotosSubViewModel>>()

    private val _navigation = LiveNavigationCoroutineImplementation<Navigation>()
    val navigation: LiveNavigation<Navigation> = _navigation

    private var currentPage = -1
    private var loadingOrder = PhotosRepository.ListOrderBy.Latest

    init {
        loadNext()
    }

    private fun loadNext() {
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
            _photos = (_photos + newList) as MutableList<PhotosSubViewModel>

            currentPage = page
        }
    }

    override fun onItemClicked(photoId: String) =
        _navigation.call { displayPhoto(photoId) }

    override fun onItemOptionsClicked(photoId: String) =
        _navigation.call { displayOptions(photoId) }

    private var toggleLikeJob: Job? = null
    override fun onItemLikeClicked(photoId: String) {
        toggleLikeJob?.let { if (it.isActive) it.cancel() }

        val photo = _photos.firstOrNull { it.photo.id == photoId } ?: return
        toggleLikeJob = viewModelScope.launch(Dispatchers.Default) {
            val isLiked = repository.toggleLike(photoId)
            photo.likedByUser = isLiked
        }
    }

    override fun onItemCollectClicked(photoId: String) =
        _navigation.call { displayAddToCollection(photoId) }

    interface Navigation {
        fun displayOptions(photoId: String)
        fun displayPhoto(photoId: String)
        fun displayAddToCollection(photoId: String)
    }
}
