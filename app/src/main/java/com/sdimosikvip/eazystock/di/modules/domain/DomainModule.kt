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
    fun provideRecommendationStockInteractor(
        stockRepository: StockRepository
    ): RecommendationStockInteractor =
        RecommendationStockInteractorImpl(stockRepository)

    @Provides
    @Singleton
    fun provideFavouriteStockInteractor(
        stockRepository: StockRepository
    ): FavouriteStockInteractor =
        FavouriteStockInteractorImpl(stockRepository)

    @Provides
    @Singleton
    fun provideHistorySearchInteractor(
        stockRepository: StockRepository
    ): HistorySearchInteractor =
        HistorySearchInteractorImpl(stockRepository)
}