package com.sdimosikvip.eazystock.di.modules

import com.sdimosikvip.eazystock.ui.detail.DetailFragment
import com.sdimosikvip.eazystock.ui.home.favourite_stocks.FavouriteFragment
import com.sdimosikvip.eazystock.ui.home.HomeFragment
import com.sdimosikvip.eazystock.ui.home.recommendation_stocks.RecommendationFragment
import com.sdimosikvip.eazystock.ui.search.SearchFragment
import com.sdimosikvip.eazystock.ui.detail.chart.ChartFragment
import com.sdimosikvip.eazystock.ui.detail.news.NewsFragment
import com.sdimosikvip.eazystock.ui.detail.summary.SummaryFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
interface FragmentBuildersModule {

    @ContributesAndroidInjector
    fun contributeRecommendationFragment(): RecommendationFragment

    @ContributesAndroidInjector
    fun contributeFavouriteFragment(): FavouriteFragment

    @ContributesAndroidInjector
    fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    fun contributeDetailFragment(): DetailFragment

    @ContributesAndroidInjector
    fun contributeNewsFragment(): NewsFragment

    @ContributesAndroidInjector
    fun contributeChartFragment(): ChartFragment

    @ContributesAndroidInjector
    fun contributeSummaryFragment(): SummaryFragment
}