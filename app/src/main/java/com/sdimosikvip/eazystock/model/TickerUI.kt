package com.sdimosikvip.eazystock.model

import com.sdimosikvip.eazystock.base.BaseDiffItem

data class TickerUI(
    val ticker: String,
    override val secondId: String = ticker
) : BaseDiffItem(false)
