package com.sdimosikvip.eazystock.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sdimosikvip.domain.interactor.FavouriteStockInteractor
import com.sdimosikvip.domain.models.FavouriteTickerDomain
import com.sdimosikvip.eazystock.base.BaseViewModel
import com.sdimosikvip.eazystock.mapper.stockDomainToUI
import com.sdimosikvip.eazystock.model.StockUI
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject


class FavouriteViewModel @Inject constructor(
    private val favouriteStockInteractor: FavouriteStockInteractor
) : BaseViewModel() {

    private val _stock = MutableLiveData<List<StockUI>>()
    val stock: LiveData<List<StockUI>> = _stock

    fun getFavouriteStocks(isFirstLoad: Boolean) {
        viewModelScope.launch(handlerException) {
            favouriteStockInteractor.getFavouriteTickers().collect {
                favouriteStockInteractor.getFavouriteStocks(it)
                    .onStart {
                        if (!isFirstLoad) setLoading()
                    }
                    .onCompletion {
                        hideLoading()
                    }
                    .collect {
                        _stock.value = it.map { item -> stockDomainToUI(item) }
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