package com.sdimosikvip.eazystock.di.modules.domain

import com.sdimosikvip.domain.interactor.*
import com.sdimosikvip.domain.repository.StockRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun provideRecommendationStocksInteractor(
        stockRepository: StockRepository
    ): RecommendationStockInteractor =
        RecommendationStockInteractorImpl(stockRepository)

    @Provides
    @Singleton
    fun provideCacheStocksInteractor(
        stockRepository: StockRepository
    ): CacheStockInteractor =
        CacheStockInteractorImpl(stockRepository)

    @Provides
    @Singleton
    fun provideFavouriteStocksInteractor(
        stockRepository: StockRepository
    ): FavouriteStockInteractor =
        FavouriteStockInteractorImpl(stockRepository)

}