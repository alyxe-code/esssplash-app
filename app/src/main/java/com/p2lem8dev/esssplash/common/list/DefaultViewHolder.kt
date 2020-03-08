package com.p2lem8dev.esssplash.common.list

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class DefaultViewHolder<T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)