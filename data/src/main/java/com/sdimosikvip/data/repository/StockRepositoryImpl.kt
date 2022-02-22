package com.sdimosikvip.data.repository

import com.sdimosikvip.data.db.FavouriteStockDAO
import com.sdimosikvip.data.db.models.FavouriteStocksDB
import com.sdimosikvip.data.sources.StockRemoteSource
import com.sdimosikvip.domain.models.*
import com.sdimosikvip.domain.repository.StockRepository
import com.sdimosikvip.domain.requireValue
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class StockRepositoryImpl @Inject constructor(
    private val remoteDataSource: StockRemoteSource,
    private val localDataSource: FavouriteStockDAO,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : StockRepository {

    override suspend fun getStocks(tickers: List<String>) =
        flow {
            coroutineScope {
                val list: MutableList<StockItemDomain> = mutableListOf()

                val listDef = mutableListOf<Deferred<Any>>()
                for (ticker in tickers) {
                    listDef.add(
                        async {
                            val stockDomain = getStock(ticker)
                            synchronized(list) {
                                list.add(stockDomain)
                            }
                        })
                }
                listDef.awaitAll()

                list.sortBy { it.stockCompanyDomain.ticker }
                emit(list)
            }
        }.flowOn(defaultDispatcher)

    private suspend fun getStock(ticker: String) = withContext(defaultDispatcher) {
        val isFavourite = localDataSource.getOneFavouriteStock(ticker) != null
        val companyStock = remoteDataSource.getCompanyStock(ticker).requireValue()
        val priceStock = remoteDataSource.getPriceStock(ticker).requireValue()
        return@withContext StockItemDomain(
            companyStock,
            priceStock,
            isFavourite
        )
    }

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
