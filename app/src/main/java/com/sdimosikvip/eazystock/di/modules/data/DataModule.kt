package com.sdimosikvip.eazystock.di.modules

import com.sdimosikvip.data.db.FavouriteStockDAO
import com.sdimosikvip.data.network.finnhub.FinnhubService
import com.sdimosikvip.data.network.finnhub.mapper.StockCompanyMapper
import com.sdimosikvip.data.network.finnhub.mapper.StockPriceMapper
import com.sdimosikvip.data.network.finnhub.models.StockCompanyResponse
import com.sdimosikvip.data.network.finnhub.models.StockPriceResponse
import com.sdimosikvip.data.network.mboum.MboumService
import com.sdimosikvip.data.network.mboum.mapper.TickersMapper
import com.sdimosikvip.data.network.mboum.models.MostWatchedTicketResponse
import com.sdimosikvip.data.repository.StockRepositoryImpl
import com.sdimosikvip.data.sources.StockRemoteSource
import com.sdimosikvip.data.sources.StockRemoteSourceImpl
import com.sdimosikvip.domain.mapper.BaseMapper
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import com.sdimosikvip.domain.models.TickersDomain
import com.sdimosikvip.domain.repository.StockRepository
import com.sdimosikvip.eazystock.di.modules.data.DatabaseModule
import com.sdimosikvip.eazystock.di.modules.data.NetworkModule
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module(
    includes = [
        NetworkModule::class,
        DatabaseModule::class,
    ]
)
class DataModule {

    @Provides
    fun provideStockCompanyResponseToStockCompanyDomain(): BaseMapper<StockCompanyResponse, StockCompanyDomain> =
        StockCompanyMapper()

    @Provides
    fun provideStockPriceResponseToStockPriceDomain(): BaseMapper<StockPriceResponse, StockPriceDomain> =
        StockPriceMapper()

    @Provides
    fun provideTickersMapper(): BaseMapper<MostWatchedTicketResponse, TickersDomain> =
        TickersMapper()

    @Provides
    fun provideStockRemoteSource(
        finnhubService: FinnhubService,
        mboumService: MboumService,
        stockCompanyMapper: BaseMapper<StockCompanyResponse, StockCompanyDomain>,
        stockPriceMapper: BaseMapper<StockPriceResponse, StockPriceDomain>,
        tickersMapper: BaseMapper<MostWatchedTicketResponse, TickersDomain>
    ): StockRemoteSource =
        StockRemoteSourceImpl(
            finnhubService,
            mboumService,
            stockCompanyMapper,
            stockPriceMapper,
            tickersMapper
        )

    @Provides
    @Reusable
    fun provideStockRepository(
        remoteDataSource: StockRemoteSource,
        favouriteStockDao: FavouriteStockDAO
    ): StockRepository = StockRepositoryImpl(remoteDataSource, favouriteStockDao)

}
