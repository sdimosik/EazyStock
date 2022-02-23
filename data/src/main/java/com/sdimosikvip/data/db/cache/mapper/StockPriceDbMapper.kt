package com.sdimosikvip.data.db.cache.mapper

import com.sdimosikvip.data.db.cache.models.PriceInfoDB
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockPriceDomain

class StockPriceDbMapper : BaseMapper<PriceInfoDB, StockPriceDomain> {
    override fun transform(o: PriceInfoDB): StockPriceDomain = StockPriceDomain(
        currentPrice = o.currentPrice,
        change = o.change,
        percentChange = o.percentChange,
        highPriceOfDay = o.highPriceOfDay,
        lowPriceOfDay = o.lowPriceOfDay,
        openPriceOfDay = o.openPriceOfDay,
        previousClosePrice = o.previousClosePrice,
        timestampResponse = o.timestampResponse,
    )

    override fun reverseTransform(o: StockPriceDomain): PriceInfoDB = PriceInfoDB(
        currentPrice = o.currentPrice,
        change = o.change,
        percentChange = o.percentChange,
        highPriceOfDay = o.highPriceOfDay,
        lowPriceOfDay = o.lowPriceOfDay,
        openPriceOfDay = o.openPriceOfDay,
        previousClosePrice = o.previousClosePrice,
        timestampResponse = o.timestampResponse,
    )
}