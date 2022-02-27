package com.sdimosikvip.eazystock.mapper

import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockItemDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import com.sdimosikvip.eazystock.model.StockCompanyUI
import com.sdimosikvip.eazystock.model.StockPriceUI
import com.sdimosikvip.eazystock.model.StockUI
import com.sdimosikvip.eazystock.utils.deltaWithPercentToString
import com.sdimosikvip.eazystock.utils.formatDayMonthTime
import com.sdimosikvip.eazystock.utils.priceWithCurrencyToString


fun stockDomainToUI(
    stockItemDomain: StockItemDomain
): StockUI = StockUI(
    isFirstId = false,
    ticker = stockItemDomain.stockCompanyDomain.ticker,
    company = stockItemDomain.stockCompanyDomain.name,
    price = priceWithCurrencyToString(
        stockItemDomain.stockPriceDomain.currentPrice,
        stockItemDomain.stockCompanyDomain.currency
    ),
    deltaDayPrice = deltaWithPercentToString(
        stockItemDomain.stockPriceDomain.change,
        stockItemDomain.stockPriceDomain.percentChange,
        stockItemDomain.stockCompanyDomain.currency
    ),
    isPositiveDelta = stockItemDomain.stockPriceDomain.change >= 0,
    timestamp = stockItemDomain.stockPriceDomain.timestampResponse,
    timestampString = formatDayMonthTime(stockItemDomain.stockPriceDomain.timestampResponse),
    isFavourite = stockItemDomain.isFavourite,
    logo = stockItemDomain.stockCompanyDomain.logo,
    secondId = stockItemDomain.stockCompanyDomain.ticker,
    stockCompanyUI = stockCompanyDomainToUI(stockItemDomain.stockCompanyDomain),
    stockPriceUI = stockPriceDomainToUI(stockItemDomain.stockPriceDomain)
)

fun stockUiToDomain(
    stockUI: StockUI
) :StockItemDomain = StockItemDomain(
    stockCompanyDomain = stockCompanyToDomain(stockUI.stockCompanyUI),
    stockPriceDomain = stockPriceToDomain(stockUI.stockPriceUI),
    isFavourite = stockUI.isFavourite
)

fun stockPriceToDomain(
    stockPriceUI: StockPriceUI
): StockPriceDomain = StockPriceDomain(
    currentPrice = stockPriceUI.currentPrice,
    change = stockPriceUI.change,
    percentChange = stockPriceUI.percentChange,
    highPriceOfDay = stockPriceUI.highPriceOfDay,
    lowPriceOfDay = stockPriceUI.lowPriceOfDay,
    openPriceOfDay = stockPriceUI.openPriceOfDay,
    previousClosePrice = stockPriceUI.previousClosePrice,
    timestampResponse = stockPriceUI.timestampResponse,
)

fun stockCompanyToDomain(
    stockCompanyUI: StockCompanyUI
): StockCompanyDomain = StockCompanyDomain(
    country = stockCompanyUI.country,
    currency = stockCompanyUI.currency,
    exchange = stockCompanyUI.exchange,
    finnhubIndustry = stockCompanyUI.finnhubIndustry,
    ipo = stockCompanyUI.ipo,
    logo = stockCompanyUI.logo,
    marketCapitalization = stockCompanyUI.marketCapitalization,
    name = stockCompanyUI.name,
    phone = stockCompanyUI.phone,
    shareOutstanding = stockCompanyUI.shareOutstanding,
    ticker = stockCompanyUI.ticker,
    weburl = stockCompanyUI.weburl
)

fun stockPriceDomainToUI(
    stockPriceDomain: StockPriceDomain
): StockPriceUI = StockPriceUI(
    currentPrice = stockPriceDomain.currentPrice,
    change = stockPriceDomain.change,
    percentChange = stockPriceDomain.percentChange,
    highPriceOfDay = stockPriceDomain.highPriceOfDay,
    lowPriceOfDay = stockPriceDomain.lowPriceOfDay,
    openPriceOfDay = stockPriceDomain.openPriceOfDay,
    previousClosePrice = stockPriceDomain.previousClosePrice,
    timestampResponse = stockPriceDomain.timestampResponse,
)

fun stockCompanyDomainToUI(
    stockCompanyDomain: StockCompanyDomain
): StockCompanyUI = StockCompanyUI(
    country = stockCompanyDomain.country,
    currency = stockCompanyDomain.currency,
    exchange = stockCompanyDomain.exchange,
    finnhubIndustry = stockCompanyDomain.finnhubIndustry,
    ipo = stockCompanyDomain.ipo,
    logo = stockCompanyDomain.logo,
    marketCapitalization = stockCompanyDomain.marketCapitalization,
    name = stockCompanyDomain.name,
    phone = stockCompanyDomain.phone,
    shareOutstanding = stockCompanyDomain.shareOutstanding,
    ticker = stockCompanyDomain.ticker,
    weburl = stockCompanyDomain.weburl,
)
