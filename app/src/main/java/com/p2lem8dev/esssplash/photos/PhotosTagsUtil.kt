package com.p2lem8dev.esssplash.photos

import java.util.*

class PhotosTagsUtil {

    private val tags = mutableListOf<Tag>()

    fun add(text: String, closable: Boolean): PhotosTagsUtil {
        val tag = Tag(
            id = UUID.randomUUID().toString(),
            text = text,
            closable = closable
        )
        tags.add(tag)
        notifyDataChanged()
        return this
    }

    fun remove(id: String) {
        tags.removeAll { it.id == id }
        notifyDataChanged()
    }

    fun get() = tags.toList()

    private fun notifyDataChanged() {
        onChange?.invoke(get())
    }

    private var onChange: ((tags: List<Tag>) -> Unit)? = null

    fun setOnChangeListener(
        onChange: (tags: List<Tag>) -> Unit
    ): PhotosTagsUtil {
        this.onChange = onChange
        return this
    }

    class Tag(
        val id: String,
        val text: String,
        val closable: Boolean
    )
}