package com.p2lem8dev.esssplash.photos

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.p2lem8dev.esssplash.R
import com.p2lem8dev.esssplash.photos.PhotosTagsUtil.Tag

object PhotosViewUtil {
    @JvmStatic
    fun getFavoriteIcon(isFavorite: Boolean, context: Context): Drawable? {
        val iconId = if (isFavorite)
            R.drawable.ic_baseline_favorite_24
        else
            R.drawable.ic_baseline_favorite_border_24
        return ContextCompat.getDrawable(context, iconId)
    }

    @JvmStatic
    fun getTextColorBySelectedTag(selected: Tag?, current: Tag?, context: Context): Int {
        val color = if (selected?.id != null && selected.id == current?.id)
            R.color.colorBlack
        else
            R.color.colorDark

        return ContextCompat.getColor(context, color)
    }

    @JvmStatic
    fun getTextFromTag(type: Tag.Type?, context: Context): String? {
        val strId = when (type) {
            Tag.Type.Popular -> R.string.popular
            Tag.Type.Following -> R.string.following
            is Tag.Type.Custom -> return type.text
            null -> return null
        }

        return context.getString(strId)
    }
}