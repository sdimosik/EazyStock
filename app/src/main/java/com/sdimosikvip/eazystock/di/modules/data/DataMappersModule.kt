package com.sdimosikvip.eazystock.di.modules.data

import com.sdimosikvip.data.db.cache.mapper.StockCompanyDbMapper
import com.sdimosikvip.data.db.cache.mapper.StockItemDbMapper
import com.sdimosikvip.data.db.cache.mapper.StockPriceDbMapper
import com.sdimosikvip.data.db.cache.models.CompanyInfoDB
import com.sdimosikvip.data.db.cache.models.PriceInfoDB
import com.sdimosikvip.data.db.cache.models.StockDB
import com.sdimosikvip.data.db.favourite.mapper.FavouriteTickerDBMapper
import com.sdimosikvip.data.db.favourite.models.FavouriteTickerDB
import com.sdimosikvip.data.network.finnhub.mapper.StockCompanyResponseMapper
import com.sdimosikvip.data.network.finnhub.mapper.StockPriceResponseMapper
import com.sdimosikvip.data.network.finnhub.models.StockCompanyResponse
import com.sdimosikvip.data.network.finnhub.models.StockPriceResponse
import com.sdimosikvip.data.network.mboum.mapper.TickersMapper
import com.sdimosikvip.data.network.mboum.models.MostWatchedTicketResponse
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataMappersModule {

    @Provides
    @Singleton
    fun provideFavouriteTickerDbToDomain(): BaseMapper<FavouriteTickerDB, FavouriteTickerDomain> =
        FavouriteTickerDBMapper()


    @Provides
    @Singleton
    fun provideStockCompanyDbToStockCompanyDomain(): BaseMapper<CompanyInfoDB, StockCompanyDomain> =
        StockCompanyDbMapper()

    @Provides
    @Singleton
    fun provideStockPriceDbToStockPriceDomain(): BaseMapper<PriceInfoDB, StockPriceDomain> =
        StockPriceDbMapper()

    @Provides
    @Singleton
    fun provideStockItemDbToStockItemDomain(
        stockCompanyDbMapper: BaseMapper<CompanyInfoDB, StockCompanyDomain>,
        stockPriceDbMapper: BaseMapper<PriceInfoDB, StockPriceDomain>
    ): BaseMapper<StockDB, StockItemDomain> =
        StockItemDbMapper(stockCompanyDbMapper, stockPriceDbMapper)

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