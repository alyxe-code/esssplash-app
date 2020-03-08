package com.p2lem8dev.esssplash.common.list

import androidx.databinding.ViewDataBinding

interface Cell<out TBinding : ViewDataBinding> {
    val layoutID: Int
    fun bind(binding: @UnsafeVariance TBinding) = Unit
    fun unbind(binding: @UnsafeVariance TBinding) = Unit
}