package com.p2lem8dev.esssplash.common.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.paging.PagedListAdapter

open class DefaultPagedListAdapter(
    private val lifecycleOwner: LifecycleOwner
) : PagedListAdapter<ComparableBinding<ViewDataBinding>, DefaultViewHolder<ViewDataBinding>>(
    DefaultDiffCallback()
) {

    override fun getItemViewType(position: Int): Int =
        getItem(position)?.layoutID ?: throw IndexOutOfBoundsException()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DefaultViewHolder<ViewDataBinding> {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        return DefaultViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DefaultViewHolder<ViewDataBinding>, position: Int) {
        getItem(position)?.bind(holder.binding)
        holder.binding.lifecycleOwner = lifecycleOwner
    }
}
