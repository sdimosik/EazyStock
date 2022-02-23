package com.sdimosikvip.data.network.finnhub.mapper

import com.sdimosikvip.data.network.finnhub.models.StockPriceResponse
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockPriceDomain

class StockPriceResponseMapper : BaseMapper<StockPriceResponse, StockPriceDomain> {
    override fun transform(o: StockPriceResponse): StockPriceDomain = StockPriceDomain(
        currentPrice = o.c,
        change = o.d,
        percentChange = o.dp,
        highPriceOfDay = o.h,
        lowPriceOfDay = o.l,
        openPriceOfDay = o.o,
        previousClosePrice = o.pc,
        timestampResponse = o.t,
    )

    override fun reverseTransform(o: StockPriceDomain): StockPriceResponse =
        StockPriceResponse(
            c = o.currentPrice,
            d = o.change,
            dp = o.percentChange,
            h = o.highPriceOfDay,
            l = o.lowPriceOfDay,
            o = o.openPriceOfDay,
            pc = o.previousClosePrice,
            t = o.timestampResponse
        )
}