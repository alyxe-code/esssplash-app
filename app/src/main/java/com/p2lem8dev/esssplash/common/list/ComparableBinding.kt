package com.p2lem8dev.esssplash.common.list

import androidx.databinding.ViewDataBinding

interface ComparableBinding<out T : ViewDataBinding> : Binding<T> {
    fun isItemSame(other: ComparableBinding<ViewDataBinding>): Boolean
    fun isContentSame(other: ComparableBinding<ViewDataBinding>): Boolean
}