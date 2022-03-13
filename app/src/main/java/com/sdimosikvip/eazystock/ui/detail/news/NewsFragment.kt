package com.sdimosikvip.eazystock.ui.detail.news

import by.kirich1409.viewbindingdelegate.viewBinding
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.databinding.FragmentNewsBinding

class NewsFragment : BaseFragment(
    tittleRes = R.string.news_fragment_name,
    layoutId = R.layout.fragment_news
) {
    override val binding by viewBinding(FragmentNewsBinding::bind)
}