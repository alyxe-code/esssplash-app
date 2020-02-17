package com.p2lem8dev.esssplash.photos.viewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.p2lem8dev.esssplash.app.App
import com.p2lem8dev.esssplash.app.ViewModelFactory
import com.p2lem8dev.esssplash.app.activity.MainActivity
import com.p2lem8dev.esssplash.databinding.PhotoViewerFragmentBinding

class PhotosViewerFragment : Fragment() {

    private val viewModel: PhotosViewerViewModel by viewModels {
        ViewModelFactory { PhotosViewerViewModel(App.appComponent.photos()) }
    }

    private val args: PhotosViewerFragmentArgs by navArgs()

    private lateinit var binding: PhotoViewerFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PhotoViewerFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.init(args.photoId)
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.setSystemUIVisibility(false)
    }
}
