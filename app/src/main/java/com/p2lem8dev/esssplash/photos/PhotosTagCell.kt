package com.p2lem8dev.esssplash.photos

import androidx.lifecycle.LiveData
import com.p2lem8dev.esssplash.R
import com.p2lem8dev.esssplash.common.list.Binding
import com.p2lem8dev.esssplash.databinding.CellPhotosTagBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

@ExperimentalCoroutinesApi
@InternalCoroutinesApi
class PhotosTagCell(
    private val viewModel: PhotosViewModel,
    val tag: PhotosTagsUtil.Tag,
    val selectedTag: LiveData<PhotosTagsUtil.Tag>
) : Binding<CellPhotosTagBinding> {

    override val layoutID: Int = R.layout.cell_photos_tag

    val text = tag.text
    val closable = tag.closable

    override fun bind(binding: CellPhotosTagBinding) {
        super.bind(binding)
        binding.cell = this
    }

    fun onClickClose() = viewModel.deleteTag(tag.id)

    fun onClick() = viewModel.selectTag(tag.id)
}