package com.sdimosikvip.eazystock.di.modules.data

import com.sdimosikvip.data.db.favourite_tickers.mapper.FavouriteTickerDBMapper
import com.sdimosikvip.data.db.favourite_tickers.models.FavouriteTickerDB
import com.sdimosikvip.data.db.history_search.mapper.HistoryTickerDBMapper
import com.sdimosikvip.data.db.history_search.models.HistoryTickerDB
import com.sdimosikvip.data.network.finnhub.mapper.ResultResponseMapper
import com.sdimosikvip.data.network.finnhub.mapper.StockCompanyResponseMapper
import com.sdimosikvip.data.network.finnhub.mapper.StockPriceResponseMapper
import com.sdimosikvip.data.network.finnhub.mapper.SymbolLookupResponseMapper
import com.sdimosikvip.data.network.finnhub.models.ResultResponse
import com.sdimosikvip.data.network.finnhub.models.StockCompanyResponse
import com.sdimosikvip.data.network.finnhub.models.StockPriceResponse
import com.sdimosikvip.data.network.finnhub.models.SymbolLookupResponse
import com.sdimosikvip.data.network.mboum.mapper.TickersMapper
import com.sdimosikvip.data.network.mboum.models.MostWatchedTicketResponse
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class MapperDataModule {

    @Provides
    @Singleton
    fun provideFavouriteTickerDbToDomain(): BaseMapper<FavouriteTickerDB, FavouriteTickerDomain> =
        FavouriteTickerDBMapper()

    @Provides
    @Singleton
    fun provideStockCompanyResponseToStockCompanyDomain(): BaseMapper<StockCompanyResponse, StockCompanyDomain> =
        StockCompanyResponseMapper()

    @Provides
    @Singleton
    fun provideStockPriceResponseToStockPriceDomain(): BaseMapper<StockPriceResponse, StockPriceDomain> =
        StockPriceResponseMapper()

    @Provides
    @Singleton
    fun provideTickersMapper(): BaseMapper<MostWatchedTicketResponse?, TickersDomain> =
        TickersMapper()

    @Provides
    @Singleton
    fun provideResultResponseMapper(): BaseMapper<ResultResponse, ResultDomain> =
        ResultResponseMapper()

    @Provides
    @Singleton
    fun provideHistoryTickerDBMapper(): BaseMapper<HistoryTickerDB, TickerDomain> =
        HistoryTickerDBMapper()

    @Provides
    @Singleton
    fun provideSymbolLookupResponseMapper(
        resultResponseMapper: BaseMapper<ResultResponse, ResultDomain>
    ): BaseMapper<SymbolLookupResponse, SymbolLookupDomain> =
        SymbolLookupResponseMapper(resultResponseMapper)

}