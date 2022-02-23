package com.sdimosikvip.data.db.cache.mapper

import com.sdimosikvip.data.db.cache.models.CompanyInfoDB
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockCompanyDomain

class StockCompanyDbMapper : BaseMapper<CompanyInfoDB, StockCompanyDomain> {
    override fun transform(o: CompanyInfoDB): StockCompanyDomain = StockCompanyDomain(
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
        weburl = o.weburl,
    )

    override fun reverseTransform(o: StockCompanyDomain): CompanyInfoDB = CompanyInfoDB(
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