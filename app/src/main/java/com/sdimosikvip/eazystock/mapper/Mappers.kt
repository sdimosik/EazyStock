package com.sdimosikvip.eazystock.mapper

import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import com.sdimosikvip.eazystock.model.StockUI
import com.sdimosikvip.eazystock.utils.deltaWithPercentToString
import com.sdimosikvip.eazystock.utils.formatDayMonthTime
import com.sdimosikvip.eazystock.utils.priceWithCurrencyToString


fun StockCompanyAndPriceDomainToUI(
    stockCompanyDomain: StockCompanyDomain,
    stockPriceDomain: StockPriceDomain,
    isFav: Boolean
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
    timestamp = stockPriceDomain.timestampResponse,
    timestampString = formatDayMonthTime(stockPriceDomain.timestampResponse),
    isFavourite = isFav,
    logo = stockCompanyDomain.logo,
    secondId = stockCompanyDomain.ticker
)