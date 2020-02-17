package com.p2lem8dev.esssplash.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.p2lem8dev.esssplash.app.Constants

private var imageTransition: DrawableTransitionOptions? = null
private const val DEFAULT_COLOR = Color.WHITE
private const val DEFAULT_IMAGE_ROUND = 0

@BindingAdapter(value = ["image_url", "image_color"], requireAll = true)
fun setImageByUrl(imageView: ImageView, imageUrl: String?, imageColor: Int?) {
    imageUrl ?: return
    Glide
        .with(imageView.context)
        .load(imageUrl)
        .transform(CenterCrop())
        .placeholder(ColorDrawable(imageColor ?: Color.WHITE))
        .transition(
            imageTransition
                ?: DrawableTransitionOptions
                    .withCrossFade(Constants.IMAGE_TRANSITION_DURATION)
                    .also { imageTransition = it })
        .into(imageView)
}

@BindingAdapter("image_url_avatar")
fun setAvatarImageByUrl(imageView: ImageView, imageUrl: String?) {
    imageUrl ?: return
    Glide
        .with(imageView.context)
        .load(imageUrl)
        .transform(CenterCrop(), CircleCrop())
        .placeholder(ColorDrawable(DEFAULT_COLOR))
        .transition(
            imageTransition
                ?: DrawableTransitionOptions
                    .withCrossFade(Constants.IMAGE_TRANSITION_DURATION)
                    .also { imageTransition = it })
        .into(imageView)
}
