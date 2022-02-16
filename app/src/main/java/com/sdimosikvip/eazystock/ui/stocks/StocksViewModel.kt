package com.sdimosikvip.eazystock.ui.stocks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sdimosikvip.domain.common.Outcome
import com.sdimosikvip.domain.interactor.GetStockUseCase
import com.sdimosikvip.domain.models.StockCompanyDomain
import kotlinx.coroutines.launch
import javax.inject.Inject

class StocksViewModel @Inject constructor(
    private val getStockUseCase: GetStockUseCase
) : ViewModel() {

    private val _stock = MutableLiveData<Outcome<StockCompanyDomain>>()
    val stock = _stock

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

    init {
        viewModelScope.launch {
            getStockUseCase.execute("AAPL").collect {
                _stock.value = it
            }
        }
    }
}