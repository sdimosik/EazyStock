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
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
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

    @Volatile
    private var count = 2

    init {
        /*viewModelScope.launch(handlerException) {
            favouriteStockInteractor.getFavouriteTickers().collect { listFavTicker ->
                recommendationStockInteractor.execute()
                    .onStart { setLoading() }
                    .onCompletion { if (--count <= 0) hideLoading() }
                    .map { it.map { item -> stockDomainToUI(item) } }
                    .collect {
                        _stockTop.value = it
                    }

                favouriteStockInteractor.getFavouriteStocks(listFavTicker)
                    .onStart { setLoading() }
                    .onCompletion { if (--count <= 0) hideLoading() }
                    .map { it.map { item -> stockDomainToUI(item) } }
                    .collect {
                        _stockFav.value = it
                    }
            }
        }*/
    }

    fun fetch(listFavTicker: List<FavouriteTickerDomain>) {

        viewModelScope.launch(handlerException) {
            Timber.tag("homeviewmodel").i("start fetch")
            Timber.tag("homeviewmodel").i("state: ${state.value}")
            setLoading()
            Timber.tag("homeviewmodel").i("state: ${state.value}")

            val diff = mutableListOf<Deferred<Any>>()

            diff.add(
                async {
                    _stockTop.value = recommendationStockInteractor.execute()
                        .single()
                        .map { item -> stockDomainToUI(item) }
                })

            diff.add(
                async {
                    _stockFav.value = favouriteStockInteractor.getFavouriteStocks(listFavTicker)
                        .single()
                        .map { item -> stockDomainToUI(item) }
                })

            Timber.tag("homeviewmodel").i("start await fetch")
            diff.awaitAll()
            Timber.tag("homeviewmodel").i("end await fetch")

            hideLoading()
            Timber.tag("homeviewmodel").i("state: ${state.value}")
            Timber.tag("homeviewmodel").i("end fetch")
        }
    }

    fun update(
        isFirstLoad: Boolean = false,
    ) {
        viewModelScope.launch(handlerException) {
            Timber.tag("homeviewmodel").i("start update")
            val list = favouriteStockInteractor.getFavouriteTickersOneshot()
            fetch(list)
            Timber.tag("homeviewmodel").i("end update")
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