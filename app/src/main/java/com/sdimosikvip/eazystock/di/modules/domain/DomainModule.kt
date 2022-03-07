package com.sdimosikvip.eazystock.di.modules.domain

import com.sdimosikvip.domain.interactor.FavouriteStockInteractor
import com.sdimosikvip.domain.interactor.FavouriteStockInteractorImpl
import com.sdimosikvip.domain.interactor.RecommendationStockInteractor
import com.sdimosikvip.domain.interactor.RecommendationStockInteractorImpl
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
}