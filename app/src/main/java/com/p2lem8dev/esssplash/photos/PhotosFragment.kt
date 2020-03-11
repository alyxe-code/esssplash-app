package com.p2lem8dev.esssplash.photos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.p2lem8dev.esssplash.R
import com.p2lem8dev.esssplash.app.App
import com.p2lem8dev.esssplash.app.ViewModelFactory
import com.p2lem8dev.esssplash.app.activity.MainActivity
import com.p2lem8dev.esssplash.common.*
import com.p2lem8dev.esssplash.common.list.DefaultListAdapter
import com.p2lem8dev.esssplash.common.list.DefaultPagedListAdapter
import com.p2lem8dev.esssplash.databinding.FragmentPhotosBinding
import com.p2lem8dev.esssplash.photos.options.PhotosOptionsFragment
import com.p2lem8dev.unsplashapi.models.Photo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
@ExperimentalCoroutinesApi
class PhotosFragment : Fragment(),
    PhotosViewModel.Navigation {

    private val viewModel: PhotosViewModel by viewModels {
        ViewModelFactory {
            PhotosViewModel(
                App.appComponent.photosRepository()
            )
        }
    }

    private lateinit var binding: FragmentPhotosBinding
    private lateinit var imagesAdapter: DefaultPagedListAdapter
    private lateinit var tagsAdapter: DefaultListAdapter<ViewDataBinding>

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

        imagesAdapter = DefaultPagedListAdapter(viewLifecycleOwner)
        binding.imagesRecycler.adapter = imagesAdapter
        binding.imagesRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                stopEditor()
            }
        })

        tagsAdapter = DefaultListAdapter(emptyList(), viewLifecycleOwner)
        binding.tagsRecycler.adapter = tagsAdapter

        binding.search.callback = viewModel
        binding.search.lifecycleOwner = viewLifecycleOwner
        binding.search.editor.setOnEditorActionListener { _, actionId, _ ->
            return@setOnEditorActionListener when (actionId) {
                EditorInfo.IME_ACTION_SEARCH,
                EditorInfo.IME_ACTION_DONE -> {
                    viewModel.onSubmit()
                    true
                }
                else -> false
            }
        }

        viewModel.photos.observe(viewLifecycleOwner, imagesAdapter::submitList)
        viewModel.navigation.observe(viewLifecycleOwner, this)
        viewModel.tagsList.observe(viewLifecycleOwner, this::updateTagsList)
        viewModel.selectedTag.observe(viewLifecycleOwner, Observer { stopEditor() })
    }

    override fun stopEditor() {
        binding.search.editor.clearFocus()
        (activity as? MainActivity)?.hideKeyboard()
    }

    private fun updateTagsList(tags: List<PhotosTagsUtil.Tag>?) {
        tags ?: return
        val newList = tags.map {
            PhotosTagCell(
                viewModel = viewModel,
                tag = it,
                selectedTag = viewModel.selectedTag
            )
        }
        tagsAdapter.submitList(newList)
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
            .show(
                childFragmentManager,
                OPTIONS_DIALOG_TAG
            )
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
