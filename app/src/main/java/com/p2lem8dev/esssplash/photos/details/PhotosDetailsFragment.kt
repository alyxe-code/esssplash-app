package com.p2lem8dev.esssplash.photos.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.p2lem8dev.esssplash.app.ViewModelFactory
import com.p2lem8dev.esssplash.databinding.FragmentPhotosDetailsBinding
import com.p2lem8dev.esssplash.photos.PhotosViewModel

class PhotosDetailsFragment : Fragment() {
    private val viewModel: PhotosDetailsViewModel by viewModels {
        ViewModelFactory { PhotosDetailsViewModel() }
    }

    private lateinit var binding: FragmentPhotosDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotosDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }

}