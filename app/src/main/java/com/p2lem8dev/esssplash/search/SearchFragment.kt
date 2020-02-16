package com.p2lem8dev.esssplash.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.p2lem8dev.esssplash.app.App
import com.p2lem8dev.esssplash.app.ViewModelFactory
import com.p2lem8dev.esssplash.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    private val viewModel: SearchViewModel by viewModels {
        ViewModelFactory { SearchViewModel(App.appComponent.search()) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }
}
