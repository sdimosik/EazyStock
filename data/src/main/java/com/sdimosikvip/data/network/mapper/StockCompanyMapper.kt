package com.sdimosikvip.data.network.mapper

import com.sdimosikvip.data.network.models.StockCompanyResponse
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockCompanyDomain

class StockCompanyMapper : BaseMapper<StockCompanyResponse, StockCompanyDomain> {
    
    override fun transformToDomain(type: StockCompanyResponse): StockCompanyDomain = StockCompanyDomain(
        country = type.country,
        currency = type.currency,
        exchange = type.exchange,
        finnhubIndustry = type.finnhubIndustry,
        ipo = type.ipo,
        logo = type.logo,
        marketCapitalization = type.marketCapitalization,
        name = type.name,
        phone = type.phone,
        shareOutstanding = type.shareOutstanding,
        ticker = type.ticker,
        weburl = type.weburl,
    )
}