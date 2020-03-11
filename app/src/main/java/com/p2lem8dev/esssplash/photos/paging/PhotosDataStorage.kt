package com.p2lem8dev.esssplash.photos.paging

import com.p2lem8dev.esssplash.common.list.DataStorage
import com.p2lem8dev.unsplashapi.models.Photo
import java.util.concurrent.CopyOnWriteArrayList

class PhotosDataStorage(private val onChanged: () -> Unit) : DataStorage<Photo> {

    private val cache = CopyOnWriteArrayList<Photo>()
    override val size: Int get() = cache.count()

    override fun save(items: List<Photo>) {
        cache.addAll(items)
        onChanged()
    }

    override fun clear() {
        cache.clear()
        onChanged()
    }

    override fun all(): List<Photo> = cache.toList()

    override fun get(startPosition: Int, size: Int): List<Photo> = when {
        size < 0 || size == 0 -> emptyList()
        startPosition < 0 -> emptyList()
        startPosition > cache.size -> emptyList()
        startPosition + size > cache.size -> cache.subList(startPosition, cache.size)
        else -> cache.subList(startPosition, startPosition + size)
    }
}