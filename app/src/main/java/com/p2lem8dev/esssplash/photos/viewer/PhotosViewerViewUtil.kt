package com.p2lem8dev.esssplash.photos.viewer

import android.content.Context
import com.p2lem8dev.esssplash.R

object PhotosViewerViewUtil {
    @JvmStatic
    fun username(properties: PhotosViewerViewModel.Properties?, context: Context): String? =
        properties?.let { context.getString(R.string.username, it.username) }
}
