package com.sdimosikvip.eazystock.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sdimosikvip.eazystock.di.ViewModelFactory
import com.sdimosikvip.eazystock.di.ViewModelKey
import com.sdimosikvip.eazystock.ui.favourite_stocks.FavouriteViewModel
import com.sdimosikvip.eazystock.ui.home.HomeViewModel
import com.sdimosikvip.eazystock.ui.recommendation_stocks.RecommendationViewModel
import com.sdimosikvip.eazystock.ui.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(RecommendationViewModel::class)
    fun bindRecommendationViewModel(recommendationViewModel: RecommendationViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteViewModel::class)
    fun bindFavouriteViewModel(favouriteViewModel: FavouriteViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}