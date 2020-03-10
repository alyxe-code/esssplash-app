package com.p2lem8dev.esssplash.photos.paging

import android.util.Log
import com.p2lem8dev.esssplash.common.list.DataStorage
import com.p2lem8dev.unsplashapi.models.Photo
import java.util.concurrent.CopyOnWriteArrayList

class PhotosDataStorage(private val onChanged: () -> Unit) : DataStorage<Photo> {

    private val cache = CopyOnWriteArrayList<Photo>()
    override val size: Int get() = cache.count()

    override fun save(items: List<Photo>) {
        cache.addAll(items)
        Log.d("PHOTOS", "Cache size increased to ${cache.size} | $size")
        onChanged()
    }

    override fun clear() {
        cache.clear()
        onChanged()
    }

    override fun all(): List<Photo> = cache.toList()

    override fun get(startPosition: Int, size: Int): List<Photo> {
        if (startPosition > cache.size) return emptyList()
        if (startPosition + size > cache.size)
            return cache.subList(startPosition, cache.size)
        return cache.subList(startPosition, startPosition + size)
    }
}