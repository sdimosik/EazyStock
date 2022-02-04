package com.sdimosikvip.eazystock.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment(
    @StringRes val tittleRes: Int,
    @LayoutRes layoutId: Int
) : Fragment(layoutId) {

    protected abstract val binding: ViewBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        subscribe()
    }

    protected open fun setupViews() {}

    protected open fun subscribe() {}

    protected fun showError(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_SHORT).show()
    }
}