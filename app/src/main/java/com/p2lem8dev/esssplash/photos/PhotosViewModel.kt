package com.p2lem8dev.esssplash.photos

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.p2lem8dev.esssplash.common.NotAuthorizedException
import com.p2lem8dev.esssplash.common.ResourceNotFound
import com.p2lem8dev.esssplash.common.ServerException
import com.p2lem8dev.esssplash.common.UndefinedException
import com.p2lem8dev.esssplash.common.livenavigation.LiveNavigation
import com.p2lem8dev.esssplash.common.livenavigation.LiveNavigationCoroutineImplementation
import com.p2lem8dev.unsplashapi.repository.PhotosRepository
import kotlinx.coroutines.*
import retrofit2.HttpException

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
        viewModelScope.launch(Dispatchers.Default) {
            val page = currentPage + 1
            val photos = try {
                repository.listPhotos(page, 10, loadingOrder)
            } catch (t: Throwable) {
                null
            }

            Log.d(TAG, "Loaded ${photos?.size ?: 0} photos")
            photos ?: return@launch

            val newList = withContext(Dispatchers.Default) {
                photos.map { photo ->
                    PhotosSubViewModel(
                        parentViewModel = this@PhotosViewModel,
                        photo = photo
                    )
                }
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
    override fun onItemLikeClicked(photoId: String, isLiked: Boolean) {
        toggleLikeJob?.let { if (it.isActive) it.cancel() }

        val photo = _photos.firstOrNull { it.id == photoId } ?: return
        viewModelScope.launch(Dispatchers.Default) {
            val updatedPhoto = try {
                if (isLiked)
                    repository.unlikePhoto(photoId)
                else
                    repository.likePhoto(photoId)
            } catch (e: CancellationException) {
                Log.d(TAG, "Toggle like has been canceled")
                null
            } catch (e: HttpException) {
                val exception = when (e.code()) {
                    401 -> NotAuthorizedException
                    404 -> ResourceNotFound
                    500 -> ServerException
                    else -> UndefinedException
                }
                _navigation.call { displayHttpException(exception) }
                null
            } catch (e: Exception) {
                _navigation.call { displayException(e) }
                null
            }

            updatedPhoto ?: return@launch

            photo.updateLikes(updatedPhoto.likedByUser, updatedPhoto.likes)
        }
    }

    override fun onItemCollectClicked(photoId: String) =
        _navigation.call { displayAddToCollection(photoId) }

    interface Navigation {
        fun displayOptions(photoId: String)
        fun displayPhoto(photoId: String)
        fun displayAddToCollection(photoId: String)

        fun displayException(exception: Exception)
        fun displayHttpException(exception: Exception)
    }

    companion object {
        private const val TAG = "PHOTOS"
    }
}
