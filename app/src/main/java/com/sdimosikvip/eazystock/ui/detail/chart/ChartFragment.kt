package com.sdimosikvip.eazystock.ui.detail.chart

import androidx.core.content.ContextCompat.getColor
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.databinding.FragmentChartBinding
import com.sdimosikvip.eazystock.ui.detail.DetailViewModel

class ChartFragment : BaseFragment(
    tittleRes = R.string.chart_fragment_name,
    layoutId = R.layout.fragment_chart
) {
    override val binding by viewBinding(FragmentChartBinding::bind)

    private val detailViewModel: DetailViewModel by viewModels(
        ownerProducer = { requireParentFragment() },
        factoryProducer = { viewModelFactory }
    )

    override fun setupViews() {
        super.setupViews()

        with(binding) {
            currentPriceTextview.text = detailViewModel.stockUI.price
            dayDeltaPriceTextview.text = detailViewModel.stockUI.deltaDayPrice

            if (detailViewModel.stockUI.isPositiveDelta) {
                dayDeltaPriceTextview.setTextColor(getColor(requireContext(), R.color.plus_price))
            } else {
                dayDeltaPriceTextview.setTextColor(getColor(requireContext(), R.color.minus_price))
            }
        }
    }
}