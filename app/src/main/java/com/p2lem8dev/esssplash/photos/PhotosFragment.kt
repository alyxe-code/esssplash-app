package com.p2lem8dev.esssplash.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.p2lem8dev.esssplash.app.App
import com.p2lem8dev.esssplash.app.ViewModelFactory
import com.p2lem8dev.esssplash.databinding.FragmentPhotosBinding

class PhotosFragment : Fragment() {

    private val viewModel: PhotosViewModel by viewModels {
        ViewModelFactory { PhotosViewModel(App.appComponent.home()) }
    }

    private lateinit var binding: FragmentPhotosBinding
    private lateinit var adapter: PhotosListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        adapter = PhotosListAdapter(viewLifecycleOwner)
        adapter.state.observe(viewLifecycleOwner, this::onAdapterStateChanged)
        binding.recycler.adapter = adapter

        viewModel.parts.observe(viewLifecycleOwner, this::updatePhotosList)
    }

    private fun onAdapterStateChanged(state: PhotosListAdapter.State) = when (state) {
        PhotosListAdapter.State.ItemAtStartLoaded -> Unit
        PhotosListAdapter.State.ItemAtEndLoaded -> viewModel.loadNext()
    }

    private fun updatePhotosList(list: List<PhotosSubViewModel>?) {
        list ?: return
        adapter.submitList(list)
    }
}
