package com.p2lem8dev.esssplash.photos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.p2lem8dev.esssplash.databinding.CellPhotoBinding

class PhotosListAdapter(
    private val lifecycleOwner: LifecycleOwner
) : RecyclerView.Adapter<PhotosListAdapter.ViewHolder>() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    private var dataSource = mutableListOf<PhotosSubViewModel>()

    override fun getItemCount(): Int = dataSource.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = CellPhotoBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (position) {
            0 -> _state.setValue(State.ItemAtStartLoaded)
            itemCount - 1 -> _state.setValue(State.ItemAtEndLoaded)
        }

        val model = dataSource.getOrNull(position)
        model ?: return
        holder.bindTo(model, lifecycleOwner)
    }

    fun submitList(list: List<PhotosSubViewModel>) {
        dataSource = mutableListOf<PhotosSubViewModel>().also { it.addAll(list) }
        notifyDataSetChanged()
    }

    class ViewHolder(
        private val binding: CellPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTo(viewModel: PhotosSubViewModel, lifecycleOwner: LifecycleOwner) {
            binding.viewModel = viewModel
            binding.lifecycleOwner = lifecycleOwner
        }
    }

    enum class State {
        ItemAtStartLoaded,
        ItemAtEndLoaded
    }
}
