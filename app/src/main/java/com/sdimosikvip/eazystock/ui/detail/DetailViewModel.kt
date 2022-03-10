package com.sdimosikvip.eazystock.ui.detail

import com.sdimosikvip.eazystock.base.BaseViewModel
import com.sdimosikvip.eazystock.model.StockUI
import javax.inject.Inject

class DetailViewModel @Inject constructor(

) : BaseViewModel() {

    var stockUI: StockUI = StockUI.EMPTY
}