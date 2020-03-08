package com.p2lem8dev.esssplash.photos

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.p2lem8dev.esssplash.common.NotAuthorizedException
import com.p2lem8dev.esssplash.common.ResourceNotFound
import com.p2lem8dev.esssplash.common.ServerException
import com.p2lem8dev.esssplash.common.UndefinedException
import com.p2lem8dev.esssplash.common.livenavigation.LiveNavigation
import com.p2lem8dev.esssplash.common.livenavigation.LiveNavigationCoroutineImplementation
import com.p2lem8dev.esssplash.photos.paging.PhotosDataSourceFactory
import com.p2lem8dev.unsplashapi.repository.PhotosRepository
import kotlinx.coroutines.*
import retrofit2.HttpException

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class PhotosViewModel(private val repository: PhotosRepository) : ViewModel(), PhotosSubViewModel.Navigation {

    val photosPagedList = PhotosDataSourceFactory(
        repository = repository,
        scope = viewModelScope
    ).map {
        val viewModel = PhotosSubViewModel(it, this)
        PhotosCell(viewModel)
    }.toLiveData(
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(10)
            .build()
    )

    private val _navigation = LiveNavigationCoroutineImplementation<Navigation>()
    val navigation: LiveNavigation<Navigation> = _navigation

    override fun onItemClicked(photoId: String) =
        _navigation.call { displayPhoto(photoId) }

    override fun onItemOptionsClicked(photoId: String) =
        _navigation.call { displayOptions(photoId) }

    private var toggleLikeJob: Job? = null
    override fun onItemLikeClicked(self: PhotosSubViewModel, photoId: String, isLiked: Boolean) {
        toggleLikeJob?.let { if (it.isActive) it.cancel() }

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

            self.updateLikes(updatedPhoto.likedByUser, updatedPhoto.likes)
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
