package com.p2lem8dev.esssplash.photos.paging

import androidx.paging.PositionalDataSource
import com.p2lem8dev.unsplashapi.models.Photo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

class PhotosDataSource(
    private val repository: PhotosPagedRepository,
    private val scope: CoroutineScope
) : PositionalDataSource<Photo>() {

    private val count = AtomicInteger(0)

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<Photo>) {
        scope.launch(Dispatchers.Default) {
            val items = try {
                load(params.startPosition, params.loadSize)
            } catch (e: Exception) {
                emptyList<Photo>()
            }

            callback.onResult(items)
        }
    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<Photo>) {
        scope.launch(Dispatchers.Default) {
            val items = try {
                load(params.requestedStartPosition, params.pageSize)
            } catch (e: Exception) {
                emptyList<Photo>()
            }

            callback.onResult(items, 0, items.size)
        }
    }

    private suspend fun load(startPosition: Int, size: Int): List<Photo> {
        val items = if (startPosition == 0)
            repository.loadPage(0, size)
        else {
            val page = count.get() / size
            repository.loadPage(page, size)
        }

        count.addAndGet(items.size)
        return items
    }
}