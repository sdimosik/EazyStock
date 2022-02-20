package com.sdimosikvip.eazystock.ui.adapters.delegates

import android.animation.Animator
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
import com.sdimosikvip.eazystock.model.StockUI


object StocksDelegates {

    fun lightAndDarkAdapterDelegate(glide: RequestManager) =
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

                    if (item.isPositiveDelta) {
                        dayDeltaPriceTextview.setTextColor(getColor(R.color.plus_price))
                    } else {
                        dayDeltaPriceTextview.setTextColor(getColor(R.color.minus_price))
                    }

                    // it's plg now
                    // TODO + add dialog confirm
                    val isFav = true
                    if (isFav) {
                        favouriteImageview.setImageResource(R.drawable.star_active)
                        favouriteImageview.contentDescription = "fav"
                    } else {
                        favouriteImageview.setImageResource(R.drawable.star_inactive)
                        favouriteImageview.contentDescription = "not_fav"
                    }

                    favouriteImageview.setOnClickListener {
                        if (favouriteImageview.contentDescription == "not_fav") {
                            favouriteImageview.setImageResource(R.drawable.star_active)
                            favouriteImageview.contentDescription = "fav"
                        } else if (favouriteImageview.contentDescription == "fav") {
                            favouriteImageview.setImageResource(R.drawable.star_inactive)
                            favouriteImageview.contentDescription = "not_fav"
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
}