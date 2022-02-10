package com.sdimosikvip.eazystock.ui.stocks

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.sdimosikvip.domain.common.ResultResponse
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.databinding.FragmentStocksBinding
import com.sdimosikvip.eazystock.mapper.StockDomainToStockUIMapper
import com.sdimosikvip.eazystock.model.StockUI
import com.sdimosikvip.eazystock.ui.adapters.AsyncListDifferAdapter
import com.sdimosikvip.eazystock.ui.adapters.delegates.StocksDelegates
import com.sdimosikvip.eazystock.utils.setup


class StocksFragment() : BaseFragment(
    tittleRes = R.string.stocks_fragment_name,
    layoutId = R.layout.fragment_stocks
) {

    companion object {
        const val TITTLE_ID = R.string.stocks_fragment_name
    }

    override val binding by viewBinding(FragmentStocksBinding::bind)
    private val stocksViewModel: StocksViewModel by viewModels {
        viewModelFactory
    }
    private val adapter by lazy {
        AsyncListDifferAdapter(
            AdapterDelegatesManager(StocksDelegates.lightAndDarkAdapterDelegate())
        )
    }

    override fun setupViews() {
        super.setupViews()

        with(binding) {
            shimmerRecyclerView.setup(adapter, R.layout.shimmer_item_stock)
        }
    }

    override fun subscribe() {
        super.subscribe()

        stocksViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textHome.text = it
        })

        stocksViewModel.stock.observe(viewLifecycleOwner) {
            when (it.status) {
                ResultResponse.Status.LOADING -> {
                    binding.shimmerRecyclerView.showShimmer()
                }
                ResultResponse.Status.SUCCESS -> {
                    with(it.data!!) {
                        adapter.items = listOf(StockDomainToStockUIMapper().transformToDomain(this))
                    }
                    binding.shimmerRecyclerView.hideShimmer()
                }
                ResultResponse.Status.ERROR -> {
                    binding.shimmerRecyclerView.hideShimmer()
                }
            }
        }
    }
}