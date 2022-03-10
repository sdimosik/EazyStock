package com.sdimosikvip.eazystock.ui.detail.chart

import by.kirich1409.viewbindingdelegate.viewBinding
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.databinding.FragmentChartBinding

class ChartFragment : BaseFragment(
    tittleRes = R.string.chart_fragment_name,
    layoutId = R.layout.fragment_chart
) {
    override val binding by viewBinding(FragmentChartBinding::bind)
}