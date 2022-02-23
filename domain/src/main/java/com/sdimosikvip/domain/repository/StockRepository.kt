package com.sdimosikvip.domain.repository

import com.sdimosikvip.domain.models.FavouriteTickerDomain
import com.sdimosikvip.domain.models.StockItemDomain
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getStocks(
        favouriteTickerDomainList: List<FavouriteTickerDomain>
    ): Flow<MutableList<StockItemDomain>>

    suspend fun saveFavouriteStock(favouriteTickerDomain: FavouriteTickerDomain)

    suspend fun deleteFavouriteStock(favouriteTickerDomain: FavouriteTickerDomain)

    suspend fun getFavouriteStocks(): Flow<List<FavouriteTickerDomain>>
}