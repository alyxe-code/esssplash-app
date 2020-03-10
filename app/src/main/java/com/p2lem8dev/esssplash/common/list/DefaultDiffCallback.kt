package com.p2lem8dev.esssplash.common.list

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil

class DefaultDiffCallback<T : ViewDataBinding> : DiffUtil.ItemCallback<ComparableBinding<T>>() {
    override fun areItemsTheSame(
        oldItem: ComparableBinding<T>,
        newItem: ComparableBinding<T>
    ): Boolean = oldItem.isItemSame(newItem)

    override fun areContentsTheSame(
        oldItem: ComparableBinding<T>,
        newItem: ComparableBinding<T>
    ): Boolean = oldItem.isContentSame(newItem)
}