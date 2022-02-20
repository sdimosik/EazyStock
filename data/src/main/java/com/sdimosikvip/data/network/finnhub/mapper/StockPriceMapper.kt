package com.sdimosikvip.data.network.finnhub.mapper

import com.sdimosikvip.data.network.finnhub.models.StockPriceResponse
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockPriceDomain

class StockPriceMapper : BaseMapper<StockPriceResponse, StockPriceDomain> {
    override fun transform(type: StockPriceResponse): StockPriceDomain = StockPriceDomain(
        currentPrice = type.c,
        change = type.d,
        percentChange = type.dp,
        highPriceOfDay = type.h,
        lowPriceOfDay = type.l,
        openPriceOfDay = type.o,
        previousClosePrice = type.pc,
        timestampResponse = type.t,
    )
}