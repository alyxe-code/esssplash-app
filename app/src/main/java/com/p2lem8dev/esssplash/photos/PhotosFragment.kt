package com.p2lem8dev.esssplash.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.paging.PagedList
import com.p2lem8dev.esssplash.R
import com.p2lem8dev.esssplash.app.App
import com.p2lem8dev.esssplash.app.ViewModelFactory
import com.p2lem8dev.esssplash.common.*
import com.p2lem8dev.esssplash.common.list.ComparableBinding
import com.p2lem8dev.esssplash.common.list.DefaultPagedListAdapter
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
    private lateinit var adapter: DefaultPagedListAdapter

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

        adapter = DefaultPagedListAdapter(viewLifecycleOwner)
        binding.recycler.adapter = adapter

        viewModel.photos.observe(viewLifecycleOwner, adapter::submitList)
        viewModel.navigation.observe(viewLifecycleOwner, this)
    }

    private fun submitList(pagedList: PagedList<ComparableBinding<ViewDataBinding>>?) {
        pagedList ?: return
        val prevPosition = binding.recycler.scrollY
        adapter.submitList(pagedList)
        binding.recycler.scrollToPosition(prevPosition)
    }

    private var optionsFragment: Fragment? = null
    override fun displayOptions(photoId: String) = childFragmentManager.commit {
        val fragment = PhotosOptionsFragment.newInstance(photoId, this@PhotosFragment::hideOptions)
        optionsFragment = fragment
        replace(binding.options.id, fragment)
    }

    private fun hideOptions() = childFragmentManager.commit {
        val fragment = optionsFragment ?: return@commit
        remove(fragment)
        optionsFragment = null
    }

    override fun displayPhoto(photoId: String) = TODO("Not implemented")

    override fun displayAddToCollection(photoId: String): Unit = TODO("Not yet implemented")

    override fun displayException(exception: Exception) = showException(exception)

    override fun displayHttpException(exception: Exception) {
        val stringId = when (exception) {
            NotAuthorizedException -> R.string.http_error_not_authorized
            ResourceNotFound -> R.string.http_error_resource_not_found
            ServerException -> R.string.http_error_server_exception
            else -> R.string.error
        }
        showSnackbar(binding.root, getString(stringId))
    }
}
