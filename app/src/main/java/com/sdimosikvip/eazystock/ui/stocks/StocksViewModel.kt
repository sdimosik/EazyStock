package com.sdimosikvip.eazystock.ui.stocks

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sdimosikvip.domain.interactor.CacheStockInteractor
import com.sdimosikvip.domain.interactor.FavouriteStockInteractor
import com.sdimosikvip.domain.interactor.RecommendationStockInteractor
import com.sdimosikvip.domain.models.FavouriteTickerDomain
import com.sdimosikvip.eazystock.base.BaseViewModel
import com.sdimosikvip.eazystock.mapper.stockDomainToUI
import com.sdimosikvip.eazystock.mapper.stockUiToDomain
import com.sdimosikvip.eazystock.model.StockUI
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class StocksViewModel @Inject constructor(
    private val recommendationStockInteractor: RecommendationStockInteractor,
    private val cacheStockInteractor: CacheStockInteractor,
    private val favouriteStockInteractor: FavouriteStockInteractor
) : BaseViewModel() {

    private val _stock = MutableLiveData<List<StockUI>>()
    val stock: LiveData<List<StockUI>> = _stock

    fun getRecommendationStocks(isFirstLoad: Boolean) {
        viewModelScope.launch {
            Log.d("view", "getRecommendationStocks")
            /* favouriteStockInteractor.get().collect {
                 recommendationStockInteractor.getRecommendationStock()
                     .onStart {
                         if (!isFirstLoad) setLoading()
                     }
                     .onCompletion {
                         hideLoading()
                     }
                     .map { it.map { stockDomainToUI(it) } }
                     .collect {
                         _stock.value = it
                     }
             }*/
            recommendationStockInteractor.getRecommendationStock()
                .onStart {
                    if (!isFirstLoad) setLoading()
                }
                .onCompletion {
                    hideLoading()
                }
                .map { it.map { stockDomainToUI(it) } }
                .collect {
                    _stock.value = it
                }
        }
    }

    fun addFavouriteStock(stockUI: StockUI) {
        viewModelScope.launch {
            cacheStockInteractor.saveStock(stockUiToDomain(stockUI))
            favouriteStockInteractor.add(FavouriteTickerDomain(stockUI.ticker))
        }
    }

    fun deleteFavouriteStock(stockUI: StockUI) {
        viewModelScope.launch {
            cacheStockInteractor.saveStock(stockUiToDomain(stockUI))
            favouriteStockInteractor.delete(FavouriteTickerDomain(stockUI.ticker))
        }
    }
}