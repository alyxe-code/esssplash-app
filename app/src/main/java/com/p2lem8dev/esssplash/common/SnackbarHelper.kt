package com.p2lem8dev.esssplash.common

import android.view.View
import com.google.android.material.snackbar.Snackbar

private var snackbar: Snackbar? = null

fun showSnackbar(view: View, message: String?) {
    message ?: return
    snackbar?.dismiss()
    snackbar = Snackbar
        .make(view, message, Snackbar.LENGTH_LONG)
        .setAnimationMode(Snackbar.ANIMATION_MODE_SLIDE)
        .also { it.show() }
}