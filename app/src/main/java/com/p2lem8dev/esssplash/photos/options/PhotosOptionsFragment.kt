package com.p2lem8dev.esssplash.photos.options

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.p2lem8dev.esssplash.R
import com.p2lem8dev.esssplash.common.showToast
import com.p2lem8dev.esssplash.databinding.FragmentPhotosOptionsBinding
import java.io.Serializable

class PhotosOptionsFragment : DialogFragment(), AdapterView.OnItemClickListener {

    companion object {
        fun newInstance(model: PhotoModel) = PhotosOptionsFragment().apply {
            arguments = PhotosOptionsFragmentArgs
                .Builder(model)
                .build()
                .toBundle()
        }
    }

    private lateinit var binding: FragmentPhotosOptionsBinding

    private val args: PhotosOptionsFragmentArgs by navArgs()

    enum class Option(@StringRes val stringId: Int) {
        CopyLink(R.string.photos_options_copy_link),
        ShareTo(R.string.photos_options_share_to),
        HideImage(R.string.photos_options_hide)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPhotosOptionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fragment = this
        binding.listView.adapter = ArrayAdapter(
            view.context,
            R.layout.cell_photos_options,
            Option.values().map { getString(it.stringId) }
        )
        binding.listView.onItemClickListener = this
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val option = Option.values().getOrNull(position) ?: return
        when (option) {
            Option.CopyLink -> copyLink()
            Option.ShareTo -> shareTo()
            Option.HideImage -> hideImage()
        }
    }

    fun closeOptions() = findNavController().popBackStack()

    private fun showMessage(message: String) = showToast(binding.root.context, message)

    private fun copyLink() {
        val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newRawUri(
            args.model.title,
            Uri.parse(args.model.sharableLink)
        )

        clipboard.setPrimaryClip(clip)

        showMessage(getString(R.string.photos_options_sharable_link_copied))
    }

    private fun shareTo() = showMessage("Share to!")

    private fun hideImage() = showMessage("Hide image!")

    class PhotoModel(
        val photoId: String,
        val sharableLink: String,
        val title: String
    ) : Serializable
}
