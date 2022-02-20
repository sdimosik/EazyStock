package com.sdimosikvip.eazystock.utils

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.todkars.shimmer.ShimmerRecyclerView

fun <VH : RecyclerView.ViewHolder> ShimmerRecyclerView.setup(
    adapter: RecyclerView.Adapter<VH>,
    @LayoutRes layoutId: Int
) {
    this.adapter = adapter
    setItemViewType { _, _ -> layoutId }
}