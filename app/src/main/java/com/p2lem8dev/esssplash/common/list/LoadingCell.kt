package com.p2lem8dev.esssplash.common.list

import androidx.databinding.ViewDataBinding
import com.p2lem8dev.esssplash.R
import com.p2lem8dev.esssplash.databinding.CellLoadingBinding

class LoadingCell : ComparableBinding<CellLoadingBinding> {
    override val layoutID: Int = R.layout.cell_loading

    override fun isItemSame(other: ComparableBinding<ViewDataBinding>): Boolean =
        other.layoutID == layoutID

    override fun isContentSame(other: ComparableBinding<ViewDataBinding>): Boolean = true
}
