package com.sdimosikvip.eazystock.ui.detail.summary

import by.kirich1409.viewbindingdelegate.viewBinding
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.databinding.FragmentSummaryBinding

class SummaryFragment : BaseFragment(
    tittleRes = R.string.summary_fragment_name,
    layoutId = R.layout.fragment_summary
) {
    override val binding by viewBinding(FragmentSummaryBinding::bind)
}