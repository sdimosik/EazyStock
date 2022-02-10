package com.sdimosikvip.eazystock.ui.adapters.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseDiffItem
import com.sdimosikvip.eazystock.databinding.ItemStockBinding
import com.sdimosikvip.eazystock.model.StockUI


object StocksDelegates {

    fun lightAndDarkAdapterDelegate() =
        adapterDelegateViewBinding<StockUI, BaseDiffItem, ItemStockBinding>(
            { layoutInflater, parent -> ItemStockBinding.inflate(layoutInflater, parent, false) }
        ) {
            bind {
                with(binding) {
                    when (layoutPosition % 2 == 0) {
                        true -> stockCard.setCardBackgroundColor(getColor(R.color.item_stock_1))
                        false -> stockCard.setCardBackgroundColor(getColor(R.color.item_stock_2))
                    }

                    tickerTextview.text = item.ticker
                    companyTextview.text = item.company
                    currentPriceTextview.text = item.price
                    dayDeltaPriceTextview.text = item.deltaDayPrice
                }
            }
        }
}