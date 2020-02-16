package com.p2lem8dev.esssplash.common

import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.p2lem8dev.esssplash.app.Constants

private var imageTransition: DrawableTransitionOptions? = null

@BindingAdapter(value = ["image_url", "color"], requireAll = true)
fun setImageByUrl(imageView: ImageView, imageUrl: String, color: Int) {
    Log.d("PHOTOS", "Going to download image $imageUrl")
    Glide
        .with(imageView.context)
        .load(imageUrl)
        .centerCrop()
        .placeholder(ColorDrawable(color))
        .transition(
            imageTransition
                ?: DrawableTransitionOptions
                    .withCrossFade(Constants.IMAGE_TRANSITION_DURATION)
                    .also { imageTransition = it })
        .into(imageView)
}
