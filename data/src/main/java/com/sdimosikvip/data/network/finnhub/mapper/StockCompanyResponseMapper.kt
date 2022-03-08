package com.sdimosikvip.data.network.finnhub.mapper

import com.sdimosikvip.data.network.finnhub.models.StockCompanyResponse
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockCompanyDomain

class StockCompanyResponseMapper : BaseMapper<StockCompanyResponse, StockCompanyDomain> {

    override fun transform(o: StockCompanyResponse): StockCompanyDomain = StockCompanyDomain(
        country = o.country,
        currency = o.currency ?: "",
        exchange = o.exchange ?: "",
        finnhubIndustry = o.finnhubIndustry ?: "",
        ipo = o.ipo ?: "",
        logo = o.logo ?: "",
        marketCapitalization = o.marketCapitalization ?: 0.0,
        name = o.name ?: "",
        phone = o.phone ?: "",
        shareOutstanding = o.shareOutstanding ?: 0.0,
        ticker = o.ticker ?: "nothing",
        weburl = o.weburl ?: "",
    )

    override fun reverseTransform(o: StockCompanyDomain): StockCompanyResponse =
        StockCompanyResponse(
            country = o.country,
            currency = o.currency,
            exchange = o.exchange,
            finnhubIndustry = o.finnhubIndustry,
            ipo = o.ipo,
            logo = o.logo,
            marketCapitalization = o.marketCapitalization,
            name = o.name,
            phone = o.phone,
            shareOutstanding = o.shareOutstanding,
            ticker = o.ticker,
            weburl = o.weburl
        )
}