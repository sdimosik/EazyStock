package com.sdimosikvip.eazystock.mapper

import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.eazystock.model.StockUI

class StockDomainToStockUIMapper : BaseMapper<StockCompanyDomain, StockUI> {
    override fun transformToDomain(type: StockCompanyDomain): StockUI =
        StockUI(
            isFirstId = false,
            ticker = type.ticker,
            company = type.name,
            price = "TODO",
            deltaDayPrice = "TODO",
            logo = type.logo,
            secondId = type.ticker
        )
}