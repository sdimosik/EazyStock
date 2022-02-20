package com.sdimosikvip.data.repository

import com.sdimosikvip.data.db.FavouriteStockDAO
import com.sdimosikvip.data.db.models.FavouriteStocksDB
import com.sdimosikvip.data.sources.StockRemoteSource
import com.sdimosikvip.domain.models.FavouriteStocksDomain
import com.sdimosikvip.domain.models.StockCompanyDomain
import com.sdimosikvip.domain.models.StockPriceDomain
import com.sdimosikvip.domain.models.TickersDomain
import com.sdimosikvip.domain.repository.StockRepository
import com.sdimosikvip.domain.requireValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val remoteDataSource: StockRemoteSource,
    private val localDataSource: FavouriteStockDAO,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : StockRepository {

    override suspend fun getCompanyStock(
        ticker: String
    ): StockCompanyDomain =
        withContext(defaultDispatcher) { remoteDataSource.getCompanyStock(ticker).requireValue() }

    override suspend fun getPriceStock(
        ticker: String
    ): StockPriceDomain =
        withContext(defaultDispatcher) { remoteDataSource.getPriceStock(ticker).requireValue() }

    override suspend fun getMostWatchedTickers(): TickersDomain =
        withContext(defaultDispatcher) { remoteDataSource.getMostWatcherTickers().requireValue() }

    override suspend fun saveFavouriteStock(ticker: String) =
        withContext(defaultDispatcher) {
            localDataSource.insertFavouriteStock(
                FavouriteStocksDB(ticker)
            )
        }

    override suspend fun deleteFavouriteStock(ticker: String) =
        withContext(defaultDispatcher) {
            localDataSource.deleteFavoriteStock(FavouriteStocksDB(ticker))
        }

    override suspend fun getFavouriteStocks(): Flow<List<FavouriteStocksDomain>> =
        localDataSource.getFavouriteStock().map {
            it.map { stock ->
                FavouriteStocksDomain(stock.ticker)
            }
        }.flowOn(defaultDispatcher)
}
