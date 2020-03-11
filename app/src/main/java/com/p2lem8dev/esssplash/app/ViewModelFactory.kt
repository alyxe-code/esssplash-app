package com.p2lem8dev.esssplash.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

open class ViewModelFactory<VM : ViewModel>(
    private val construct: () -> VM
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <VM : ViewModel?> create(modelClass: Class<VM>): VM =
        construct() as? VM ?: throw IllegalStateException()
}