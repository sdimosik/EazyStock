package com.sdimosikvip.eazystock.ui.stocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sdimosikvip.domain.interactor.RecommendationStockInteractor
import com.sdimosikvip.eazystock.base.BaseViewModel
import com.sdimosikvip.eazystock.mapper.StockCompanyAndPriceDomainToUI
import com.sdimosikvip.eazystock.model.StockUI
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class StocksViewModel @Inject constructor(
    private val recommendationStockInteractor: RecommendationStockInteractor
) : BaseViewModel() {

    private val _stock = MutableLiveData<List<StockUI>>()
    val stock: LiveData<List<StockUI>> = _stock

    init {
        viewModelScope.launch(handlerException) {
            recommendationStockInteractor.execute().onStart {
                setLoading()
            }.onCompletion {
                hideLoading()
            }.collect {
                _stock.value = it.map { itemList ->
                    StockCompanyAndPriceDomainToUI(
                        itemList.stockCompanyDomain,
                        itemList.stockPriceDomain
                    )
                }
            }
        }
    }
}