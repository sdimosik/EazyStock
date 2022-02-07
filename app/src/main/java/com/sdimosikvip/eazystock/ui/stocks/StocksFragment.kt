package com.sdimosikvip.eazystock.ui.stocks

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hannesdorfmann.adapterdelegates4.AdapterDelegatesManager
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.databinding.FragmentStocksBinding
import com.sdimosikvip.eazystock.model.Stock
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
            shimmerRecyclerView.setup(adapter, R.layout.item_stock)

            adapter.items = listOf(
                Stock(1, "YNDX 1", "Yandex", "1545 Р", "+34 Р (1,15%)"),
                Stock(2, "YNDX 2", "Yandex", "1545 Р", "+34 Р (1,15%)"),
                Stock(3, "YNDX 3", "Yandex", "1545 Р", "+34 Р (1,15%)"),
                Stock(4, "YNDX 4", "Yandex", "1545 Р", "+34 Р (1,15%)")
            )

            shimmerRecyclerView.hideShimmer()
        }
    }

    override fun subscribe() {
        super.subscribe()

        stocksViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textHome.text = it
        })
    }
}