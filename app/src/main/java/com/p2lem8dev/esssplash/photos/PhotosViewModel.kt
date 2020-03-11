package com.p2lem8dev.esssplash.photos

import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.p2lem8dev.esssplash.app.Constants
import com.p2lem8dev.esssplash.common.NotAuthorizedException
import com.p2lem8dev.esssplash.common.ResourceNotFound
import com.p2lem8dev.esssplash.common.ServerException
import com.p2lem8dev.esssplash.common.UndefinedException
import com.p2lem8dev.esssplash.common.list.ComparableBinding
import com.p2lem8dev.esssplash.common.livenavigation.LiveNavigation
import com.p2lem8dev.esssplash.common.livenavigation.LiveNavigationCoroutineImplementation
import com.p2lem8dev.esssplash.common.view.SearchCallback
import com.p2lem8dev.esssplash.photos.paging.*
import com.p2lem8dev.unsplashapi.models.Photo
import com.p2lem8dev.unsplashapi.repository.UnsplashPhotosRepository
import kotlinx.coroutines.*
import retrofit2.HttpException

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class PhotosViewModel(
    private val unsplashPhotosRepository: UnsplashPhotosRepository
) : ViewModel(),
    PhotosSubViewModel.Navigation,
    SearchCallback {

    private val repository: PhotosPagingRepository = PhotosPagingRepositoryImpl(
        storage = PhotosDataStorage(this::onDataStorageChanged),
        repository = unsplashPhotosRepository,
        config = PhotosPagingRepositoryImpl.Config(
            pageSize = Constants.PHOTOS_ITEMS_PER_PAGE,
            orderBy = PhotosPagingRepository.OrderBy.Latest
        )
    )

    private val dataSourceFactory = PhotosDataSourceFactory(repository)

    private val boundaryCallback = PhotosBoundaryCallback(
        scope = viewModelScope,
        repository = repository,
        pageSize = Constants.PHOTOS_ITEMS_PER_PAGE
    )

    private val pagedListConfig = PagedList.Config.Builder()
        .setEnablePlaceholders(false)
        .setPageSize(Constants.PHOTOS_ITEMS_PER_PAGE)
        .build()

    val photos = dataSourceFactory.map(this::buildCell).toLiveData(
        config = pagedListConfig,
        boundaryCallback = boundaryCallback
    )

    private fun buildCell(photo: Photo): ComparableBinding<ViewDataBinding> {
        val viewModel = PhotosSubViewModel(photo, this)
        return PhotosCell(viewModel)
    }

    private fun onDataStorageChanged() {
        dataSourceFactory.dataSource?.invalidate()
    }

    private val _navigation = LiveNavigationCoroutineImplementation<Navigation>()
    val navigation: LiveNavigation<Navigation> = _navigation

    val tagsList: LiveData<List<PhotosTagsUtil.Tag>> = MutableLiveData(emptyList())

    private var _selectedTag: PhotosTagsUtil.Tag? = null
        set(value) {
            field = value
            (selectedTag as MutableLiveData).postValue(value)
        }

    val selectedTag: LiveData<PhotosTagsUtil.Tag> = MutableLiveData(null)

    private val tagUtil = PhotosTagsUtil()
        .setOnChangeListener {
            (tagsList as MutableLiveData).postValue(it)
            if (_selectedTag == null) {
                _selectedTag = it.firstOrNull()
            }
        }
        .add("POPULAR", false)
        .add("FOLLOWING", false)

    override val searchQuery: MutableLiveData<String> = MutableLiveData()

    override fun onSubmit() {
        val value = searchQuery.value?.trim()
        if (!value.isNullOrEmpty())
            tagUtil.add(value, true)

        _navigation.call { stopEditor() }
    }

    fun selectTag(tagId: String) {
        val tag = tagUtil.get().firstOrNull { it.id == tagId } ?: return
        _selectedTag = tag
    }

    fun deleteTag(tagId: String) = tagUtil.remove(tagId)

    override fun onItemClicked(photoId: String) = _navigation.call { displayPhoto(photoId) }

    override fun onItemOptionsClicked(photoId: String) {
        val photo = photos
            .value
            ?.snapshot()
            ?.map { it as PhotosCell }
            ?.map { it.viewModel }
            ?.map { it.photo }
            ?.firstOrNull { it.id == photoId }
            ?: return

        _navigation.call { displayOptions(photo) }
    }

    private var toggleLikeJob: Job? = null
    override fun onItemLikeClicked(
        self: PhotosSubViewModel,
        photoId: String,
        isLiked: Boolean
    ) {
        toggleLikeJob?.let { if (it.isActive) it.cancel() }

        viewModelScope.launch(Dispatchers.Default) {
            val updatedPhoto = try {
                if (isLiked)
                    unsplashPhotosRepository.unlikePhoto(photoId)
                else
                    unsplashPhotosRepository.likePhoto(photoId)
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
        fun displayOptions(photo: Photo)
        fun displayPhoto(photoId: String)
        fun displayAddToCollection(photoId: String)

        fun displayException(exception: Exception)
        fun displayHttpException(exception: Exception)

        fun stopEditor()
    }

    companion object {
        private const val TAG = "PHOTOS"
    }
}
