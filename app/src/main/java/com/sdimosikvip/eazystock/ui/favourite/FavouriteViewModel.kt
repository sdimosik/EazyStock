package com.sdimosikvip.eazystock.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sdimosikvip.domain.interactor.FavouriteStockInteractor
import com.sdimosikvip.eazystock.base.BaseViewModel
import javax.inject.Inject


class FavouriteViewModel @Inject constructor(
    private val favouriteStockInteractor: FavouriteStockInteractor
): BaseViewModel() {


}