package com.p2lem8dev.esssplash.photoviewer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.p2lem8dev.esssplash.app.ViewModelFactory
import com.p2lem8dev.esssplash.databinding.PhotoViewerFragmentBinding

class PhotoViewerFragment : Fragment() {

    private val viewModel: PhotoViewerViewModel by viewModels {
        ViewModelFactory { PhotoViewerViewModel() }
    }

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
    }
}
