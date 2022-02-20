package com.sdimosikvip.domain.models

data class StockItemDomain(
    val stockCompanyDomain: StockCompanyDomain,
    val stockPriceDomain: StockPriceDomain
)
