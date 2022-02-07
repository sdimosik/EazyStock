package com.sdimosikvip.eazystock.di.modules

import com.sdimosikvip.eazystock.ui.favourite.FavouriteFragment
import com.sdimosikvip.eazystock.ui.stocks.StocksFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
interface FragmentBuildersModule {

    @ContributesAndroidInjector
    fun contributeStocksFragment(): StocksFragment

    @ContributesAndroidInjector
    fun contributeFavouriteFragment(): FavouriteFragment
}