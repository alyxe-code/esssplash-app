package com.p2lem8dev.esssplash.common.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView

class StaticListAdapter<T : ViewDataBinding>(
    cells: List<Cell<T>>,
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<StaticListAdapter.ViewHolder<T>>() {

    class ViewHolder<T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)

    private var cells = cells.toMutableList()
    private val visibleCells = mutableMapOf<ViewHolder<T>, Cell<T>>()

    override fun getItemViewType(position: Int): Int = cells[position].layoutID

    override fun onCreateViewHolder(parent: ViewGroup, layoutId: Int): ViewHolder<T> {
        val binding = DataBindingUtil.inflate<T>(
            LayoutInflater.from(parent.context),
            layoutId,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = cells.size

    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        val cell = cells[position]
        cell.bind(holder.binding)
        visibleCells[holder] = cell
    }

    override fun onViewAttachedToWindow(holder: ViewHolder<T>) {
        super.onViewAttachedToWindow(holder)
        holder.binding.lifecycleOwner = lifecycleOwner
    }

    override fun onViewDetachedFromWindow(holder: ViewHolder<T>) {
        super.onViewDetachedFromWindow(holder)
        holder.binding.lifecycleOwner = null
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        clearAll()
    }

    fun submitList(list: List<Cell<T>>) {
        this.cells = list.toMutableList()
        notifyDataSetChanged()
    }

    private fun clearAll() {
        visibleCells.forEach { (holder, binding) ->
            binding.unbind(holder.binding)
            holder.binding.lifecycleOwner = null
        }
        visibleCells.clear()
    }
}
