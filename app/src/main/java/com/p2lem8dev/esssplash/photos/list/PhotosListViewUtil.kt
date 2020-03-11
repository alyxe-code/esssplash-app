package com.p2lem8dev.esssplash.photos.list

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.p2lem8dev.esssplash.R

object PhotosListViewUtil {
    @JvmStatic
    fun getFavoriteIcon(isFavorite: Boolean, context: Context): Drawable? {
        val iconId = if (isFavorite)
            R.drawable.ic_baseline_favorite_24
        else
            R.drawable.ic_baseline_favorite_border_24
        return ContextCompat.getDrawable(context, iconId)
    }
}