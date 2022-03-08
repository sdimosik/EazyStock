package com.sdimosikvip.eazystock.ui.detail

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.databinding.FragmentDetailBinding
import com.sdimosikvip.eazystock.ui.MainViewModel

class DetailFragment() : BaseFragment(
    tittleRes = R.string.fragment_detail_name,
    layoutId = R.layout.fragment_detail
) {
    override val binding by viewBinding(FragmentDetailBinding::bind)
    private val detailViewModel: DetailViewModel by  viewModels {
        viewModelFactory
    }
    private val sharedViewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }


}