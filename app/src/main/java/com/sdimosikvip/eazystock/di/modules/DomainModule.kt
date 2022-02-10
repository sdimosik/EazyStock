package com.sdimosikvip.eazystock.di.modules

import com.sdimosikvip.domain.interactor.GetStockUseCase
import com.sdimosikvip.domain.interactor.GetStockUseCaseImpl
import com.sdimosikvip.domain.repository.StockRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class DomainModule {

    @Provides
    @Reusable
    fun provideGetStockUseCase(
        stockRepository: StockRepository
    ): GetStockUseCase = GetStockUseCaseImpl(stockRepository)
}