package com.sdimosikvip.data.db.cache.mapper

import com.sdimosikvip.data.db.cache.models.CompanyInfoDB
import com.sdimosikvip.data.db.cache.models.PriceInfoDB
import com.sdimosikvip.data.db.cache.models.StockDB
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockItemDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import javax.inject.Inject

class StockItemDbMapper @Inject constructor(
    private val stockCompanyDbMapper: BaseMapper<CompanyInfoDB, StockCompanyDomain>,
    private val stockPriceDbMapper: BaseMapper<PriceInfoDB, StockPriceDomain>
) : BaseMapper<StockDB, StockItemDomain> {

    override fun transform(o: StockDB): StockItemDomain =
        StockItemDomain(
            stockCompanyDomain = stockCompanyDbMapper.transform(o.companyInfo),
            stockPriceDomain = stockPriceDbMapper.transform(o.priceInfo),
            isFavourite = o.isFavourite
        )

    override fun reverseTransform(o: StockItemDomain): StockDB =
        StockDB(
            tickerId = o.stockCompanyDomain.ticker,
            timestamp = o.stockPriceDomain.timestampResponse,
            isFavourite = o.isFavourite,
            companyInfo = stockCompanyDbMapper.reverseTransform(o.stockCompanyDomain),
            priceInfo = stockPriceDbMapper.reverseTransform(o.stockPriceDomain)
        )
}