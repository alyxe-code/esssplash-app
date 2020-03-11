package com.p2lem8dev.esssplash.photos

import java.util.*

class PhotosTagsUtil {

    private val tags = mutableListOf<Tag>()

    fun add(
        type: Tag.Type,
        closable: Boolean,
        onTagAdded: ((tag: Tag) -> Unit)? = null
    ): PhotosTagsUtil {
        val tag = Tag(
            id = UUID.randomUUID().toString(),
            closable = closable,
            type = type
        )
        tags.add(tag)
        onTagAdded?.invoke(tag)
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
        val closable: Boolean,
        val type: Type
    ) {
        sealed class Type {
            object Popular : Type()
            object Following : Type()
            class Custom(val text: String) : Type()
        }
    }
}