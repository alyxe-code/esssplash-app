package com.p2lem8dev.esssplash.photos.header

import com.p2lem8dev.esssplash.R
import com.p2lem8dev.esssplash.common.list.Binding
import com.p2lem8dev.esssplash.databinding.CellPhotosHeaderTagBinding

class PhotosHeaderListTagCell(
    private val viewModel: PhotosHeaderViewModel,
    tag: PhotosHeaderViewModel.Tag
) : Binding<CellPhotosHeaderTagBinding> {

    val id = tag.id
    val text = tag.text
    val closable = tag.closable

    override val layoutID: Int = R.layout.cell_photos_header_tag

    override fun bind(binding: CellPhotosHeaderTagBinding) {
        super.bind(binding)
        binding.cell = this
    }

    fun onClickClose() = viewModel.deleteTag(tagId = id)
}