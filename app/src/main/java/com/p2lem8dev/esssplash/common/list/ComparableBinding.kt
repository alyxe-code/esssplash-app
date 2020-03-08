package com.p2lem8dev.esssplash.common.list

import androidx.databinding.ViewDataBinding

interface ComparableBinding<out T : ViewDataBinding> : Binding<T> {
    fun areItemsTheSame(other: ComparableBinding<ViewDataBinding>): Boolean
    fun areContentsTheSame(other: ComparableBinding<ViewDataBinding>): Boolean
}