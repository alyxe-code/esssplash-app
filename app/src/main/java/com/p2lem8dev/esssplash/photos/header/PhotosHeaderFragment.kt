package com.p2lem8dev.esssplash.photos.header

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.p2lem8dev.esssplash.app.App
import com.p2lem8dev.esssplash.app.ViewModelFactory
import com.p2lem8dev.esssplash.databinding.FragmentPhotosHeaderBinding

class PhotosHeaderFragment : Fragment() {

    private val viewModel: PhotosHeaderViewModel by viewModels {
        ViewModelFactory {
            PhotosHeaderViewModel(
                repository = App.appComponent.photosRepository()
            )
        }
    }

    private lateinit var binding: FragmentPhotosHeaderBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotosHeaderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
    }

}