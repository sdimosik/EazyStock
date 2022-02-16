package com.sdimosikvip.eazystock.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sdimosikvip.eazystock.di.ViewModelFactory
import com.sdimosikvip.eazystock.di.ViewModelKey
import com.sdimosikvip.eazystock.ui.favourite.FavouriteViewModel
import com.sdimosikvip.eazystock.ui.stocks.StocksViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(StocksViewModel::class)
    fun bindStocksViewModel(stocksViewModel: StocksViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavouriteViewModel::class)
    fun bindFavouriteViewModel(favouriteViewModel: FavouriteViewModel): ViewModel

    @Binds
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}