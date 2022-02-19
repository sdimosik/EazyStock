package com.sdimosikvip.eazystock.ui.stocks

import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.base.BaseViewModel
import com.sdimosikvip.eazystock.databinding.FragmentStocksBinding
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

    private val glide: RequestManager by lazy {
        Glide.with(this)
    }

    private val adapter: AsyncListDifferAdapter by lazy {
        AsyncListDifferAdapter(
            AdapterDelegatesManager(StocksDelegates.lightAndDarkAdapterDelegate(glide))
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

        stocksViewModel.state.observe(viewLifecycleOwner) {
            when (it) {
                is BaseViewModel.State.Init -> {
                    binding.shimmerRecyclerView.showShimmer()
                }
                is BaseViewModel.State.IsLoading -> {
                    if (it.isLoading) {
                        binding.shimmerRecyclerView.showShimmer()
                    } else {
                        binding.shimmerRecyclerView.hideShimmer()
                    }
                }
                is BaseViewModel.State.ShowToast -> {
                    // красивый toast с инфой
                    //showError(binding.root, getString(it.messageRes))
                }
            }
        }

        stocksViewModel.stock.observe(viewLifecycleOwner) {
            adapter.items = listOf(it)
        }
    }
}