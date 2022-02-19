package com.sdimosikvip.eazystock.ui.stocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sdimosikvip.domain.interactor.RecommendationStockInteractor
import com.sdimosikvip.eazystock.base.BaseViewModel
import com.sdimosikvip.eazystock.mapper.stockCompanyAndPriceDomainToUI
import com.sdimosikvip.eazystock.model.StockUI
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class StocksViewModel @Inject constructor(
    private val recommendationStockInteractor: RecommendationStockInteractor
) : BaseViewModel() {

    private val _stock = MutableLiveData<StockUI>()
    val stock: LiveData<StockUI> = _stock

    init {
        viewModelScope.launch(handlerException) {

             flow {
                 val companyStockDeferred =
                     async { recommendationStockInteractor.getCompanyInfo("AAPL") }
                 val priceStockDeferred =
                     async { recommendationStockInteractor.getPriceInfo("AAPL") }
                 emit(
                     stockCompanyAndPriceDomainToUI(
                         companyStockDeferred.await(),
                         priceStockDeferred.await()
                     )
                 )
             }.onStart {
                 setLoading()
             }.onCompletion {
                 hideLoading()
             }.collect {
                 _stock.value = it
             }
        }

    }
}