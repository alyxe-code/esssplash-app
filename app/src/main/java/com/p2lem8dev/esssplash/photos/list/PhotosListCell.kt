package com.p2lem8dev.esssplash.photos.list

import androidx.databinding.ViewDataBinding
import com.p2lem8dev.esssplash.R
import com.p2lem8dev.esssplash.common.OnResourceLoadedCallback
import com.p2lem8dev.esssplash.common.list.ComparableBinding
import com.p2lem8dev.esssplash.databinding.CellPhotoBinding

class PhotosListCell(
    val viewModel: PhotosListSubViewModel
) : ComparableBinding<CellPhotoBinding>,
    OnResourceLoadedCallback {

    override val layoutID: Int = R.layout.cell_photo

    override fun bind(binding: CellPhotoBinding) {
        super.bind(binding)
        binding.viewModel = viewModel
        binding.cell = this
    }

    override fun isContentSame(other: ComparableBinding<ViewDataBinding>): Boolean =
        (other as? PhotosListCell)?.viewModel?.photo === viewModel.photo

    override fun isItemSame(other: ComparableBinding<ViewDataBinding>): Boolean =
        (other as? PhotosListCell)?.viewModel?.id === viewModel.id

    override fun onResourceLoaded() = Unit
}