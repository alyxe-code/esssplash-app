package com.p2lem8dev.esssplash.photos

import androidx.databinding.ViewDataBinding
import com.p2lem8dev.esssplash.R
import com.p2lem8dev.esssplash.common.list.ComparableBinding
import com.p2lem8dev.esssplash.databinding.CellPhotoBinding

class PhotosCell(private val viewModel: PhotosSubViewModel) : ComparableBinding<CellPhotoBinding> {

    override val layoutID: Int = R.layout.cell_photo

    override fun bind(binding: CellPhotoBinding) {
        super.bind(binding)
        binding.viewModel = viewModel
    }

    override fun areContentsTheSame(other: ComparableBinding<ViewDataBinding>): Boolean = false

    override fun areItemsTheSame(other: ComparableBinding<ViewDataBinding>): Boolean =
        (other as? PhotosCell)?.viewModel?.id == viewModel.id
}
