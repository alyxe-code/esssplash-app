package com.p2lem8dev.esssplash.photos.header

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.p2lem8dev.esssplash.common.view.SearchCallback
import com.p2lem8dev.unsplashapi.repository.UnsplashPhotosRepository
import java.util.*

class PhotosHeaderViewModel(
    private val repository: UnsplashPhotosRepository
) : ViewModel(), SearchCallback {

    override val searchQuery = MutableLiveData<String>()

    private var tags = mutableListOf(
        Tag(UUID.randomUUID().toString(), "NEW", false),
        Tag(UUID.randomUUID().toString(), "POPULAR", false)
    )
        set(value) {
            field = value
            (tagsList as MutableLiveData).postValue(value)
        }

    val tagsList: LiveData<List<Tag>> = MutableLiveData(tags)

    class Tag(val id: String, val text: String, val closable: Boolean)

    fun deleteTag(tagId: String) {
        val item = tags.firstOrNull { it.id == tagId }
        tags.remove(item)
        tags = tags
    }

    override fun onSubmit() {
        val value = searchQuery.value?.trim()
        if (value.isNullOrBlank())
            return

        tags.add(
            Tag(
                id = UUID.randomUUID().toString(),
                text = value,
                closable = true
            )
        )
        tags = tags
    }

    companion object {
        private const val TAG = "PHOTOS_HEADER"
    }
}