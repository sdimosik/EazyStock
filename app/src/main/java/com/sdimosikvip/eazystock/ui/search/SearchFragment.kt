package com.sdimosikvip.eazystock.ui.search

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import android.widget.EditText
import com.sdimosikvip.eazystock.R
import com.sdimosikvip.eazystock.base.BaseFragment
import com.sdimosikvip.eazystock.databinding.FragmentSearchBinding
import com.sdimosikvip.eazystock.ui.MainActivity


class SearchFragment() : BaseFragment(
    tittleRes = R.string.fragment_search_name,
    layoutId = R.layout.fragment_search,
) {
    override val binding by viewBinding(FragmentSearchBinding::bind)

    override fun setupViews() {
        super.setupViews()

        with(binding.search) {
            backImage.setOnClickListener {
                findNavController().navigateUp()
            }

            crossImage.setOnClickListener {
                input.text = null
            }

            (requireActivity() as MainActivity).showSoftKeyboard(input)
            input.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // Do Nothing
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    if (s == null || s.isEmpty()) {
                        crossImage.visibility = View.GONE
                        return
                    }

                    crossImage.visibility = View.VISIBLE
                }

                override fun afterTextChanged(s: Editable?) {
                    // Do Nothing
                }
            })
        }
    }
}