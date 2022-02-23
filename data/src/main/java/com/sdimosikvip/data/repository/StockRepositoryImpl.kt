package com.sdimosikvip.data.repository

import com.sdimosikvip.data.db.FavouriteTickerDAO
import com.sdimosikvip.data.db.models.FavouriteTickerDB
import com.sdimosikvip.data.sources.StockRemoteSource
import com.sdimosikvip.domain.mapper.BaseMapper
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
    private val localDataSource: FavouriteTickerDAO,
    private val favouriteTickerDBMapper: BaseMapper<FavouriteTickerDB, FavouriteTickerDomain>,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : StockRepository {

    override suspend fun getStocks(favouriteTickerDomainList: List<FavouriteTickerDomain>) =
        flow {
            coroutineScope {
                val list: MutableList<StockItemDomain> = mutableListOf()

                val listDef = mutableListOf<Deferred<Any>>()
                for (ticker in favouriteTickerDomainList) {
                    listDef.add(
                        async {
                            val stockDomain = getStock(ticker.ticker)
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

    override suspend fun saveFavouriteStock(favouriteTickerDomain: FavouriteTickerDomain) =
        withContext(defaultDispatcher) {
            localDataSource.insertFavouriteStock(
                favouriteTickerDBMapper.reverseTransform(favouriteTickerDomain)
            )
        }

    override suspend fun deleteFavouriteStock(favouriteTickerDomain: FavouriteTickerDomain) =
        withContext(defaultDispatcher) {
            localDataSource.deleteFavoriteStock(
                favouriteTickerDBMapper.reverseTransform(favouriteTickerDomain)
            )
        }

    override suspend fun getFavouriteStocks(): Flow<List<FavouriteTickerDomain>> =
        localDataSource.getFavouriteStock().map { list ->
            list.map { favouriteTickerDBMapper.transform(it) }
        }.flowOn(defaultDispatcher)
}
