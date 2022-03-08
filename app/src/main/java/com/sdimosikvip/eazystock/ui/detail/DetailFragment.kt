package com.sdimosikvip.eazystock.ui.detail

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.databinding.FragmentDetailBinding
import com.sdimosikvip.eazystock.model.StockUI
import com.sdimosikvip.eazystock.ui.MainViewModel
import timber.log.Timber

class DetailFragment() : BaseFragment(
    tittleRes = R.string.fragment_detail_name,
    layoutId = R.layout.fragment_detail
) {

    companion object {
        const val TAG = "DetailFragment"
        const val TITTLE_ID = R.string.recommendation_stocks_fragment_name
        const val TAG_FRAGMENT = "recommendation_fragment"
        private const val BASE = "com.sdimosikvip.eazystock.ui.detail"
        const val STOCK_UI = "$BASE STOCK_UI"
    }

    override val binding by viewBinding(FragmentDetailBinding::bind)
    private val detailViewModel: DetailViewModel by viewModels {
        viewModelFactory
    }
    private val sharedViewModel: MainViewModel by activityViewModels {
        viewModelFactory
    }

    override fun setupViews() {
        super.setupViews()

        with(binding) {
            backImage.setOnClickListener {
                findNavController().navigateUp()
            }

            val stockUI = arguments?.getParcelable<StockUI>(STOCK_UI)
            tickerTextview.text = stockUI?.ticker
            companyTextview.text = stockUI?.company

            if (stockUI != null) {
                if (stockUI.isFavourite) {
                    favouriteImageview.setImageResource(R.drawable.star_active_detail)
                } else {
                    favouriteImageview.setImageResource(R.drawable.star_inactive_detail)
                }
            }

            favouriteImageview.setOnClickListener {
                if (stockUI == null) return@setOnClickListener

                Timber.tag(TAG).i("stock UI is fav? ${stockUI.isFavourite}")
                if (stockUI.isFavourite) {
                    sharedViewModel.deleteFavouriteStock(stockUI)
                    favouriteImageview.setImageResource(R.drawable.star_inactive_detail)
                    stockUI.isFavourite = false
                } else {
                    sharedViewModel.addFavouriteStock(stockUI)
                    favouriteImageview.setImageResource(R.drawable.star_active_detail)
                    stockUI.isFavourite = true
                }
            }
        }
    }
}