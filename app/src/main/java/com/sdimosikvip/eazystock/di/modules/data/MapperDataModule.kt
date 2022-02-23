package com.sdimosikvip.eazystock.di.modules.data

import com.sdimosikvip.data.db.mapper.FavouriteTickerDBMapper
import com.sdimosikvip.data.db.models.FavouriteTickerDB
import com.sdimosikvip.data.network.finnhub.mapper.StockCompanyResponseMapper
import com.sdimosikvip.data.network.finnhub.mapper.StockPriceResponseMapper
import com.sdimosikvip.data.network.finnhub.models.StockCompanyResponse
import com.sdimosikvip.data.network.finnhub.models.StockPriceResponse
import com.sdimosikvip.data.network.mboum.mapper.TickersMapper
import com.sdimosikvip.data.network.mboum.models.MostWatchedTicketResponse
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.FavouriteTickerDomain
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import com.sdimosikvip.domain.models.TickersDomain
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

}