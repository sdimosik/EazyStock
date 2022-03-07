package com.sdimosikvip.eazystock.ui.adapters.delegates

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseDiffItem
import com.sdimosikvip.eazystock.databinding.ItemStockBinding
import com.sdimosikvip.eazystock.databinding.ItemTickerBinding
import com.sdimosikvip.eazystock.model.StockUI
import com.sdimosikvip.eazystock.model.TickerUI


object MainDelegates {

    fun stockLightAndDarkAdapterDelegate(
        glide: RequestManager,
        addFavourite: (StockUI) -> Unit,
        deleteFavourite: (StockUI) -> Unit
    ) =
        adapterDelegateViewBinding<StockUI, BaseDiffItem, ItemStockBinding>(
            { layoutInflater, parent -> ItemStockBinding.inflate(layoutInflater, parent, false) }
        ) {
            bind {
                with(binding) {
                    when (layoutPosition % 2 == 0) {
                        true -> container.background = getDrawable(R.drawable.stock_odd_background)
                        false -> container.background =
                            getDrawable(R.drawable.stock_even_background)
                    }

                    tickerTextview.text = item.ticker
                    companyTextview.text = item.company
                    currentPriceTextview.text = item.price
                    dayDeltaPriceTextview.text = item.deltaDayPrice
                    timePrice.text = item.timestampString

                    if (item.isPositiveDelta) {
                        dayDeltaPriceTextview.setTextColor(getColor(R.color.plus_price))
                    } else {
                        dayDeltaPriceTextview.setTextColor(getColor(R.color.minus_price))
                    }

                    if (item.isFavourite) {
                        favouriteImageview.setImageResource(R.drawable.star_active)
                    } else {
                        favouriteImageview.setImageResource(R.drawable.star_inactive)
                    }

                    favouriteImageview.setOnClickListener {
                        if (item.isFavourite) {
                            deleteFavourite(item)
                            favouriteImageview.setImageResource(R.drawable.star_inactive)
                            item.isFavourite = false
                        } else {
                            addFavourite(item)
                            favouriteImageview.setImageResource(R.drawable.star_active)
                            item.isFavourite = true
                        }
                    }

                    glide.load(item.logo)
                        .listener(object : RequestListener<Drawable> {

                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                logoImageview.setImageResource(R.drawable.fail_load_stock_image)
                                return false
                            }

                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                return false
                            }

                        })
                        .into(logoImageview)
                }
            }
        }

    fun tickerDelegate(
        changeCurrentSearchInput: (TickerUI) -> Unit
    ) = adapterDelegateViewBinding<TickerUI, BaseDiffItem, ItemTickerBinding>(
        { layoutInflater, parent -> ItemTickerBinding.inflate(layoutInflater, parent, false) }
    ) {
        bind {
            with(binding) {
                ticker.text = item.ticker
                root.setOnClickListener {
                    changeCurrentSearchInput(item)
                }
            }
        }
    }
}