package com.p2lem8dev.esssplash.common

import android.graphics.Color
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.p2lem8dev.esssplash.app.Constants

private var imageTransition: DrawableTransitionOptions? = null
private const val DEFAULT_COLOR = Color.WHITE

private val DEFAULT_TRANSITION
    get() = imageTransition ?: DrawableTransitionOptions
        .withCrossFade(Constants.IMAGE_TRANSITION_DURATION)
        .also { imageTransition = it }

@BindingAdapter("image_url")
fun setImageByUrl(imageView: ImageView, imageUrl: String?) {
    imageUrl ?: return

    Glide
        .with(imageView.context)
        .load(imageUrl)
        .transform(CenterCrop())
//        .placeholder(ColorDrawable(imageColor ?: Color.WHITE))
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
//        .placeholder(ColorDrawable(DEFAULT_COLOR))
        .transition(DEFAULT_TRANSITION)
        .into(imageView)
}
