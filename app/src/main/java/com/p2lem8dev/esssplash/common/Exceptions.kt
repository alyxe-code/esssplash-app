package com.p2lem8dev.esssplash.common

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment


object UndefinedException : Exception("Oops")


fun showException(view: View, exception: Exception?, tag: String? = null, log: Boolean = true) {
    exception ?: return

    val log2 = when (exception) {
        is UndefinedException -> {
            showSnackbar(view, exception.message)
            false
        }
        else -> true
    }

    val printLog = log || log2

    if (printLog) {
        Log.d(
            tag ?: "EXCEPTION",
            "${exception.message}\n" + exception.stackTrace.joinToString("\n")
        )
    }
}

fun Fragment.showException(exception: Exception?) {
    val view = view ?: return
    showException(view, exception)
}

interface ExceptionHandler {
    fun onException(exception: Exception): Boolean

}

fun Fragment.handleExceptionOrShow(exception: Exception?) {
    exception ?: return
    val handled = (activity as? ExceptionHandler)?.onException(exception)
    if (handled != true) {
        showException(exception)
    }
}