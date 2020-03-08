package com.p2lem8dev.esssplash.photos.options

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.p2lem8dev.esssplash.R
import com.p2lem8dev.esssplash.common.showSnackbar
import com.p2lem8dev.esssplash.databinding.PhotosOptionsBinding

class PhotosOptionsFragment : Fragment(), AdapterView.OnItemClickListener {

    companion object {
        fun newInstance(photoId: String, onCloseOptions: () -> Unit) =
            PhotosOptionsFragment().apply {
                this.photoId = photoId
                this.onCloseOptions = onCloseOptions
            }
    }

    private lateinit var binding: PhotosOptionsBinding

    private val optionsList = listOf(
        Option(
            R.string.photos_options_copy_link,
            this::copyLink
        ),
        Option(
            R.string.photos_options_share_to,
            this::shareTo
        ),
        Option(
            R.string.photos_options_hide,
            this::hideImage
        )
    )

    class Option(
        @StringRes
        val stringId: Int,
        val action: () -> Unit
    )

    private lateinit var photoId: String
    private lateinit var onCloseOptions: () -> Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PhotosOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.listView.adapter = ArrayAdapter<String>(
            view.context,
            R.layout.photos_options_cell,
            optionsList.map { getString(it.stringId) }
        )
        binding.listView.onItemClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        optionsList.getOrNull(position)?.action?.invoke()
    }

    fun closeOptions() {
        Log.d("PHOTOS_OPTIONS", "Going to close")
        onCloseOptions()
    }

    private fun show(message: String) = showSnackbar(binding.root, message)

    private fun copyLink() = show("Copy link!")

    private fun shareTo() = show("Share to!")

    private fun hideImage() = show("Hide image!")
}
