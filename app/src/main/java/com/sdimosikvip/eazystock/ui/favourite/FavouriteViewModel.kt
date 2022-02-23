package com.sdimosikvip.eazystock.ui.favourite

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sdimosikvip.domain.interactor.CacheStockInteractor
import com.sdimosikvip.domain.interactor.FavouriteStockInteractor
import com.sdimosikvip.domain.models.FavouriteTickerDomain
import com.sdimosikvip.eazystock.base.BaseViewModel
import com.sdimosikvip.eazystock.mapper.stockDomainToUI
import com.sdimosikvip.eazystock.mapper.stockUiToDomain
import com.sdimosikvip.eazystock.model.StockUI
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


class FavouriteViewModel @Inject constructor(
    private val cacheStockInteractor: CacheStockInteractor,
    private val favouriteStockInteractor: FavouriteStockInteractor,
) : BaseViewModel() {

    private val _stock = MutableLiveData<List<StockUI>>()
    val stock: LiveData<List<StockUI>> = _stock

    fun getFavouriteStocks(isFirstLoad: Boolean) {
        viewModelScope.launch {
            Log.d("view", "getFavouriteStocks")
            favouriteStockInteractor.get().collect {
                _stock.value = cacheStockInteractor.getCacheStocks()
                    .filter { it.isFavourite }
                    .map { stockDomainToUI(it) }
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