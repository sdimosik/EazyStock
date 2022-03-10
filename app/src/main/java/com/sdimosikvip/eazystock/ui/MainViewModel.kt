package com.sdimosikvip.eazystock.ui

import androidx.annotation.StringRes
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sdimosikvip.domain.interactor.FavouriteStockInteractor
import com.sdimosikvip.domain.interactor.RecommendationStockInteractor
import com.sdimosikvip.domain.models.FavouriteTickerDomain
import com.sdimosikvip.eazystock.base.BaseViewModel
import com.sdimosikvip.eazystock.model.StockUI
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val favouriteStockInteractor: FavouriteStockInteractor,
    private val recommendationStockInteractor: RecommendationStockInteractor
) : BaseViewModel() {

    val favouriteStocksLiveData = favouriteStockInteractor.getFavouriteTickers().asLiveData()

    fun addFavouriteStock(stockUI: StockUI) {
        Timber.tag(TAG).i("add fav stock")
        viewModelScope.launch {
            favouriteStockInteractor.save(FavouriteTickerDomain(stockUI.ticker))
        }
    }

    fun deleteFavouriteStock(stockUI: StockUI) {
        Timber.tag(TAG).i("delete fav stock")
        viewModelScope.launch {
            favouriteStockInteractor.delete(FavouriteTickerDomain(stockUI.ticker))
        }
    }
}