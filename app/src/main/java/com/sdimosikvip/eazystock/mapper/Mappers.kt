package com.sdimosikvip.eazystock.mapper

import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import com.sdimosikvip.eazystock.model.StockUI
import com.sdimosikvip.eazystock.utils.deltaWithPercentToString
import com.sdimosikvip.eazystock.utils.priceWithCurrencyToString


fun stockCompanyAndPriceDomainToUI(
    stockCompanyDomain: StockCompanyDomain,
    stockPriceDomain: StockPriceDomain
): StockUI = StockUI(
    isFirstId = false,
    ticker = stockCompanyDomain.ticker,
    company = stockCompanyDomain.name,
    price = priceWithCurrencyToString(stockPriceDomain.currentPrice, stockCompanyDomain.currency),
    deltaDayPrice = deltaWithPercentToString(
        stockPriceDomain.change,
        stockPriceDomain.percentChange,
        stockCompanyDomain.currency
    ),
    isPositiveDelta = stockPriceDomain.change >= 0,
    logo = stockCompanyDomain.logo,
    secondId = stockCompanyDomain.ticker
)