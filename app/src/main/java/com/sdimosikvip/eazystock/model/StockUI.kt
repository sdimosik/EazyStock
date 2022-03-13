package com.sdimosikvip.eazystock.model

import android.os.Parcelable
import com.sdimosikvip.eazystock.base.BaseDiffItem
import kotlinx.parcelize.Parcelize

@Parcelize
data class StockUI(

    override val isFirstId: Boolean,

    val ticker: String,
    val company: String?,
    val price: String,
    val deltaDayPrice: String,
    val logo: String,
    val isPositiveDelta: Boolean,
    val timestampString: String,
    val timestamp: Long,
    var isFavourite: Boolean,

    val stockCompanyUI: StockCompanyUI,
    val stockPriceUI: StockPriceUI,

    override val secondId: String = "",
    override val firstId: Long = 0,
) : BaseDiffItem(isFirstId, firstId, secondId), Parcelable {
    companion object {
        val EMPTY = StockUI(
            isFirstId = false,
            ticker = "AAPL",
            company = "AAPL",
            price = "",
            deltaDayPrice = "",
            logo = "",
            isPositiveDelta = true,
            timestampString = "",
            timestamp = 0,
            isFavourite = false,
            stockCompanyUI = StockCompanyUI.EMPTY,
            stockPriceUI = StockPriceUI.EMPTY
        )
    }
}
