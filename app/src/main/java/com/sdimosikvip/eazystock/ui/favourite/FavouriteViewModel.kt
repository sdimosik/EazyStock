package com.sdimosikvip.eazystock.ui.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sdimosikvip.eazystock.base.BaseViewModel
import javax.inject.Inject


class FavouriteViewModel @Inject constructor(

): BaseViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text
}