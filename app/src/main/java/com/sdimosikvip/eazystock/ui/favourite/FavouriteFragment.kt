package com.sdimosikvip.eazystock.ui.favourite

import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.databinding.FragmentFavouriteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouriteFragment() : BaseFragment(
    tittleRes = R.string.favourite_fragment_name,
    layoutId = R.layout.fragment_favourite
) {

    companion object{
        const val TITTLE_ID = R.string.favourite_fragment_name
    }

    override val binding by viewBinding(FragmentFavouriteBinding::bind)

    private val favouriteViewModel: FavouriteViewModel by viewModels()

    override fun subscribe() {
        super.subscribe()

        favouriteViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textDashboard.text = it
        })
    }
}