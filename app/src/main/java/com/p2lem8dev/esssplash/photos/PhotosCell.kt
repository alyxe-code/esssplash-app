package com.p2lem8dev.esssplash.photos

import com.p2lem8dev.esssplash.R
import com.p2lem8dev.esssplash.common.list.Cell
import com.p2lem8dev.esssplash.databinding.CellPhotoBinding

class PhotosCell(private val viewModel: PhotosSubViewModel) : Cell<CellPhotoBinding> {

    override val layoutID: Int = R.layout.cell_photo

    override fun bind(binding: CellPhotoBinding) {
        super.bind(binding)
        binding.viewModel = viewModel
    }
}
