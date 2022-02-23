package com.sdimosikvip.domain.interactor

import com.sdimosikvip.domain.models.FavouriteTickerDomain
import com.sdimosikvip.domain.repository.StockRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface FavouriteStockInteractor {
    fun get(): Flow<List<FavouriteTickerDomain>>

    suspend fun add(favouriteTickerDomain: FavouriteTickerDomain)

    suspend fun delete(favouriteTickerDomain: FavouriteTickerDomain)
}

class FavouriteStockInteractorImpl @Inject constructor(
    private val stockRepository: StockRepository
) : FavouriteStockInteractor {

    override fun get(): Flow<List<FavouriteTickerDomain>> = stockRepository.getFavouriteTickersFlow()

    override suspend fun add(favouriteTickerDomain: FavouriteTickerDomain) =
        stockRepository.saveFavouriteTickers(favouriteTickerDomain)

    override suspend fun delete(favouriteTickerDomain: FavouriteTickerDomain) =
        stockRepository.deleteFavouriteTickers(favouriteTickerDomain)
}