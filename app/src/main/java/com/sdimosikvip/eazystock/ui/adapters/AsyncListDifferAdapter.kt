package com.sdimosikvip.eazystock.ui.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.sdimosikvip.eazystock.base.BaseDiffItem


class AsyncListDifferAdapter(
    delegatesManager: AdapterDelegatesManager<List<BaseDiffItem>>,
) : AsyncListDifferDelegationAdapter<BaseDiffItem>(DIFF_CALLBACK, delegatesManager) {

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<BaseDiffItem> =
            object : DiffUtil.ItemCallback<BaseDiffItem>() {
                override fun areItemsTheSame(
                    oldItem: BaseDiffItem,
                    newItem: BaseDiffItem
                ): Boolean {
                    return oldItem.isIdEqual(newItem)
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(
                    oldItem: BaseDiffItem,
                    newItem: BaseDiffItem
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}