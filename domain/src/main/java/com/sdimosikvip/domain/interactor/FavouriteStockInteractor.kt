package com.sdimosikvip.domain.interactor

import com.sdimosikvip.domain.models.FavouriteTickerDomain
import com.sdimosikvip.domain.models.StockItemDomain
import com.sdimosikvip.domain.repository.StockRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FavouriteStockInteractor {
    suspend fun save(favouriteTickerDomain: FavouriteTickerDomain)
    suspend fun delete(favouriteTickerDomain: FavouriteTickerDomain)
    fun getFavouriteTickers(): Flow<List<FavouriteTickerDomain>>
    fun getFavouriteStocks(favouriteTickerDomainList: List<FavouriteTickerDomain>): Flow<List<StockItemDomain>>
}

class FavouriteStockInteractorImpl @Inject constructor(
    private val stockRepository: StockRepository,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
) : FavouriteStockInteractor {

    override suspend fun save(favouriteTickerDomain: FavouriteTickerDomain) =
        stockRepository.saveFavouriteStock(favouriteTickerDomain)

    override suspend fun delete(favouriteTickerDomain: FavouriteTickerDomain) =
        stockRepository.deleteFavouriteStock(favouriteTickerDomain)

    override fun getFavouriteTickers(): Flow<List<FavouriteTickerDomain>> =
        stockRepository.getFavouriteStocks()

    override fun getFavouriteStocks(favouriteTickerDomainList: List<FavouriteTickerDomain>) =
        stockRepository.getStocks(favouriteTickerDomainList)

}