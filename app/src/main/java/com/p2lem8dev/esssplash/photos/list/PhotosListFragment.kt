package com.p2lem8dev.esssplash.photos.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import com.p2lem8dev.esssplash.R
import com.p2lem8dev.esssplash.app.App
import com.p2lem8dev.esssplash.app.ViewModelFactory
import com.p2lem8dev.esssplash.common.*
import com.p2lem8dev.esssplash.common.list.DefaultPagedListAdapter
import com.p2lem8dev.esssplash.databinding.FragmentPhotosBinding
import com.p2lem8dev.esssplash.photos.list.options.PhotosOptionsFragment
import com.p2lem8dev.unsplashapi.models.Photo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class PhotosListFragment : Fragment(), PhotosListViewModel.Navigation {

    private val viewModel: PhotosListViewModel by viewModels {
        ViewModelFactory {
            PhotosListViewModel(
                App.appComponent.photosRepository()
            )
        }
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

    override fun displayOptions(photo: Photo) {
        val user = photo.user.name ?: photo.user.instagram ?: photo.user.twitter
        val desc = photo.description ?: photo.altDescription
        val model = PhotosOptionsFragment.PhotoModel(
            photoId = photo.id,
            sharableLink = photo.links.html,
            title = when {
                user != null && desc != null -> "$user - $desc"
                user != null && desc == null -> user
                user == null && desc != null -> desc
                else -> photo.id
            }
        )
        PhotosOptionsFragment
            .newInstance(model)
            .show(childFragmentManager, OPTIONS_DIALOG_TAG)
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

    companion object {
        private const val OPTIONS_DIALOG_TAG = "PHOTOS_LIST_OPTIONS_DIALOG"
    }
}
