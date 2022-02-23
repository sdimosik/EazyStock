package com.sdimosikvip.eazystock.ui.home

import androidx.lifecycle.viewModelScope
import com.sdimosikvip.domain.interactor.CacheStockInteractor
import com.sdimosikvip.domain.interactor.FavouriteStockInteractor
import com.sdimosikvip.eazystock.base.BaseViewModel
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val cacheStockInteractor: CacheStockInteractor,
    private val favouriteStockInteractor: FavouriteStockInteractor
) : BaseViewModel() {

    fun updateStocks(isFirstLoad: Boolean) {
        viewModelScope.launch(handlerException) {
            // Trigger for add/remove favourite
            favouriteStockInteractor.get().collect() {
                cacheStockInteractor.getCacheStocksFlow().collect {
                    // Update all cache tickers
                    cacheStockInteractor.updateOrLoadStocksFlow(it.map { it.stockCompanyDomain.ticker })
                        .onStart {
                            if (!isFirstLoad) setLoading()
                        }
                        .onCompletion {
                            hideLoading()
                        }
                        .collect {

                        }
                }
            }
        }
    }
}