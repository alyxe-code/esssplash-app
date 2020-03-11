package com.p2lem8dev.esssplash.common.view

import androidx.lifecycle.MutableLiveData

interface SearchCallback {
    val searchQuery: MutableLiveData<String>
    fun onSubmit()
}