package com.sdimosikvip.data.network.mapper

import com.sdimosikvip.data.network.models.StockCompanyResponse
import com.sdimosikvip.domain.models.StockCompanyDomain


internal fun StockCompanyResponse.toDomain(): StockCompanyDomain =
    StockCompanyDomain(
        country = this.country,
        currency = this.currency,
        exchange = this.exchange,
        finnhubIndustry = this.finnhubIndustry,
        ipo = this.ipo,
        logo = this.logo,
        marketCapitalization = this.marketCapitalization,
        name = this.name,
        phone = this.phone,
        shareOutstanding = this.shareOutstanding,
        ticker = this.ticker,
        weburl = this.weburl,
    )
