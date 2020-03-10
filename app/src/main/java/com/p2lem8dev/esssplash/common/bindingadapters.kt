package com.p2lem8dev.esssplash.common

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.p2lem8dev.esssplash.app.Constants

private var imageTransition: DrawableTransitionOptions? = null
private const val DEFAULT_COLOR = Color.WHITE

private val DEFAULT_TRANSITION
    get() = imageTransition ?: DrawableTransitionOptions
        .withCrossFade(Constants.IMAGE_TRANSITION_DURATION)
        .also { imageTransition = it }


interface OnResourceLoadedCallback {
    fun onResourceLoaded()
}

//@BindingAdapter("onResourceLoaded")
//fun setResourceLoadedListener(imageView: ImageView, onResourceLoaded: OnResourceLoadedCallback?) {
//    onResourceLoaded?.onResourceLoaded()
//}

@BindingAdapter(value = ["imageUrl", "imageOnResourceLoaded"], requireAll = false)
fun setImageByUrl(
    imageView: ImageView,
    imageUrl: String?,
    imageOnResourceLoaded: OnResourceLoadedCallback?
) {
    imageUrl ?: return

    Glide
        .with(imageView.context)
        .load(imageUrl)
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean = false

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                imageOnResourceLoaded?.onResourceLoaded()
                return false
            }
        })
        .transform(CenterCrop())
        .thumbnail(0.1f)
        .transition(DEFAULT_TRANSITION)
        .into(imageView)
}

@BindingAdapter("image_url_circle")
fun setAvatarImageByUrl(imageView: ImageView, imageUrl: String?) {
    imageUrl ?: return
    Glide
        .with(imageView.context)
        .load(imageUrl)
        .transform(CenterCrop(), CircleCrop())
        .transition(DEFAULT_TRANSITION)
        .into(imageView)
}
