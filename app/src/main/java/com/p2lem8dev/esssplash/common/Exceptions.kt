package com.p2lem8dev.esssplash.common

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment


object UndefinedException : Exception("Oops")
object NotAuthorizedException : Exception()
object ServerException : Exception()
object ResourceNotFound : Exception()

fun showException(view: View, exception: Exception?, tag: String? = null, log: Boolean = true) {
    exception ?: return

    when (exception) {
        NotAuthorizedException -> {
            showSnackbar(view, "Not authorized")
        }
        else -> showSnackbar(view, exception.message)
    }

    if (log) {
        Log.e(
            tag ?: "EXCEPTION",
            "${exception.message}\n" + exception.stackTrace.joinToString("\n")
        )
    }
}

fun Fragment.showException(exception: Exception?) {
    val view = view ?: return
    showException(view, exception)
}