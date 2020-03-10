package com.p2lem8dev.esssplash.photos.paging

import android.util.Log
import androidx.databinding.ViewDataBinding
import androidx.paging.PagedList
import com.p2lem8dev.esssplash.common.list.ComparableBinding
import com.p2lem8dev.esssplash.photos.PhotosRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PhotosBoundaryCallback(
    private val scope: CoroutineScope,
    private val repository: PhotosRepository,
    private val pageSize: Int
) : PagedList.BoundaryCallback<ComparableBinding<ViewDataBinding>>() {

    override fun onZeroItemsLoaded() {
        scope.launch(Dispatchers.Default) { repository.loadInitial() }
    }

    override fun onItemAtEndLoaded(itemAtEnd: ComparableBinding<ViewDataBinding>) {
        scope.launch(Dispatchers.Default) {
            val loadedSize = repository.loadedSize
            Log.d("PHOTOS", "BoundaryCallback ${repository.loadedSize} $pageSize")
            val completePage = loadedSize % pageSize == 0
            val page = if (completePage)
                loadedSize / pageSize
            else
                loadedSize / pageSize + 1

            repository.loadPage(page)
        }
    }
}