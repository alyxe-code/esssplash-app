package com.p2lem8dev.esssplash.photos

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.p2lem8dev.esssplash.app.App
import com.p2lem8dev.esssplash.app.ViewModelFactory
import com.p2lem8dev.esssplash.common.list.LoadingCell
import com.p2lem8dev.esssplash.common.list.StaticListAdapter
import com.p2lem8dev.esssplash.databinding.FragmentPhotosBinding
import com.p2lem8dev.esssplash.photos.options.PhotosOptionsFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class PhotosFragment : Fragment(), PhotosViewModel.Navigation {

    private val viewModel: PhotosViewModel by viewModels {
        ViewModelFactory { PhotosViewModel(App.appComponent.photos()) }
    }

    private lateinit var binding: FragmentPhotosBinding
    private lateinit var adapter: StaticListAdapter<ViewDataBinding>


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

        adapter = StaticListAdapter(emptyList(), viewLifecycleOwner)
        binding.recycler.adapter = adapter

        viewModel.photos.observe(viewLifecycleOwner, this::updatePhotosList)
        viewModel.navigation.observe(viewLifecycleOwner, this)
    }

    private fun updatePhotosList(list: List<PhotosSubViewModel>?) {
        list ?: return
        val newList = list.map(::PhotosCell) + LoadingCell()
        adapter.submitList(newList)
    }

    private var optionsFragment: Fragment? = null
    override fun displayOptions(photoId: String) = childFragmentManager.commit {
        val fragment = PhotosOptionsFragment.newInstance(photoId, this@PhotosFragment::hideOptions)
        optionsFragment = fragment
        replace(binding.options.id, fragment)
    }

    private fun hideOptions() = childFragmentManager.commit {
        Log.d("PHOTOS_OPTIONS", "Closing...")
        val fragment = optionsFragment ?: return@commit
        remove(fragment)
        optionsFragment = null
    }

    override fun displayPhoto(photoId: String) = TODO("Not implemented")

    override fun displayAddToCollection(photoId: String) {
        TODO("Not yet implemented")
    }
}
