package com.sdimosikvip.eazystock.model

import com.sdimosikvip.eazystock.base.BaseDiffItem

data class StockUI(

    override val isFirstId: Boolean,

    val ticker: String,
    val company: String,
    val price: String,
    val deltaDayPrice: String,
    val logo: String,

    override val secondId: String = "",
    override val firstId: Long = 0,

) : BaseDiffItem(isFirstId, firstId, secondId)
