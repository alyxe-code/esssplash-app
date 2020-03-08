package com.p2lem8dev.esssplash.common.list

import androidx.databinding.ViewDataBinding

interface Binding<out T : ViewDataBinding> {
    val layoutID: Int
    fun bind(binding: @UnsafeVariance T) = Unit
    fun unbind(binding: @UnsafeVariance T) = Unit
}
