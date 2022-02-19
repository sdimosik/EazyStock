package com.sdimosikvip.eazystock.di.modules

import com.sdimosikvip.domain.interactor.RecommendationStockInteractor
import com.sdimosikvip.domain.interactor.RecommendationStockInteractorImpl
import com.sdimosikvip.domain.repository.StockRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class DomainModule {

    @Provides
    @Reusable
    fun provideRecommendationStockInteractor(
        stockRepository: StockRepository
    ): RecommendationStockInteractor =
        RecommendationStockInteractorImpl(stockRepository)
}