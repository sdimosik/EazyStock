package com.sdimosikvip.eazystock.mapper

import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import com.sdimosikvip.eazystock.model.StockUI

// TODO price
//  поставить ограничения на textview
fun stockCompanyAndPriceDomainToUI(
    stockCompanyDomain: StockCompanyDomain,
    stockPriceDomain: StockPriceDomain
): StockUI = StockUI(
    isFirstId = false,
    ticker = stockCompanyDomain.ticker,
    company = stockCompanyDomain.name,
    price = stockPriceDomain.currentPrice.toString(),
    deltaDayPrice = stockPriceDomain.change.toString(),
    logo = stockCompanyDomain.logo,
    secondId = stockCompanyDomain.ticker
)