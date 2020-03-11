package com.p2lem8dev.esssplash.common

import android.content.Context
import android.view.View
import android.widget.Toast
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

private var toast: Toast? = null

fun showToast(context: Context, message: String?) {
    message ?: return
    toast?.cancel()
    toast = Toast
        .makeText(context, message, Toast.LENGTH_SHORT)
        .also { it.show() }
}