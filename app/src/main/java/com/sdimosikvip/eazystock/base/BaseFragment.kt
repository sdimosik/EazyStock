package com.sdimosikvip.eazystock.base

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.sdimosikvip.eazystock.R
import dagger.android.support.DaggerFragment
import io.github.muddz.styleabletoast.StyleableToast
import javax.inject.Inject

abstract class BaseFragment(
    @StringRes val tittleRes: Int,
    @LayoutRes layoutId: Int
) : DaggerFragment(layoutId) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected abstract val binding: ViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        subscribe()
    }

    protected open fun setupViews() {}

    protected open fun subscribe() {}

    protected fun showError(view: View, msg: String) {
        StyleableToast.makeText(requireContext(), msg, Toast.LENGTH_SHORT, R.style.ErrorToast)
            .show()
    }
}