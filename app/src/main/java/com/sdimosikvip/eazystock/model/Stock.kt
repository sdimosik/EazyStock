package com.sdimosikvip.eazystock.model

import com.sdimosikvip.eazystock.base.BaseDiffItem

data class Stock(
    override var id: Long,
    val ticker: String,
    val company: String,
    val price: String,
    val deltaDayPrice: String
) : BaseDiffItem(id)
