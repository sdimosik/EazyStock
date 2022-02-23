package com.sdimosikvip.eazystock.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sdimosikvip.domain.interactor.FavouriteStockInteractor
import com.sdimosikvip.domain.interactor.RecommendationStockInteractor
import com.sdimosikvip.domain.models.FavouriteTickerDomain
import com.sdimosikvip.eazystock.base.BaseViewModel
import com.sdimosikvip.eazystock.mapper.stockDomainToUI
import com.sdimosikvip.eazystock.model.StockUI
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val favouriteStockInteractor: FavouriteStockInteractor,
    private val recommendationStockInteractor: RecommendationStockInteractor
) : BaseViewModel() {

    private val _stockTop = MutableLiveData<List<StockUI>>()
    val stockTop: LiveData<List<StockUI>> = _stockTop

    private val _stockFav = MutableLiveData<List<StockUI>>()
    val stockFav: LiveData<List<StockUI>> = _stockFav

    private var count = 2

    fun update(
        isFirstLoad: Boolean = false,
    ) {
        count = 2
        viewModelScope.launch(handlerException) {
            favouriteStockInteractor.getFavouriteTickers().collect { listFavTicker ->
                recommendationStockInteractor.execute()
                    .onStart { if (!isFirstLoad) setLoading() }
                    .onCompletion { if (--count <= 0) hideLoading() }
                    .map { it.map { item -> stockDomainToUI(item) } }
                    .collect {
                        _stockTop.value = it
                    }

                favouriteStockInteractor.getFavouriteStocks(listFavTicker)
                    .onStart { if (!isFirstLoad) setLoading() }
                    .onCompletion { if (--count <= 0) hideLoading() }
                    .map { it.map { item -> stockDomainToUI(item) } }
                    .collect {
                        _stockFav.value = it
                    }
            }
        }
    }

    fun addFavouriteStock(stockUI: StockUI) {
        viewModelScope.launch {
            favouriteStockInteractor.save(FavouriteTickerDomain(stockUI.ticker))
        }
    }

    fun deleteFavouriteStock(stockUI: StockUI) {
        viewModelScope.launch {
            favouriteStockInteractor.delete(FavouriteTickerDomain(stockUI.ticker))
        }
    }
}