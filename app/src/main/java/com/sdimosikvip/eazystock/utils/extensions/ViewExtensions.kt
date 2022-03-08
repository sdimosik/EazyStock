package com.sdimosikvip.eazystock.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.todkars.shimmer.ShimmerRecyclerView
import kotlinx.coroutines.*

fun <VH : RecyclerView.ViewHolder> ShimmerRecyclerView.setup(
    adapter: RecyclerView.Adapter<VH>,
    @LayoutRes layoutId: Int
) {
    this.adapter = adapter
    setItemViewType { _, _ -> layoutId }
}

fun EditText.afterTextChangedDebounce(delayMillis: Long, input: (String) -> Unit) {
    var lastInput = ""
    var debounceJob: Job? = null
    val uiScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            if (editable == null) {
                return
            }
            val newtInput = editable.toString()
            debounceJob?.cancel()
            if (lastInput == newtInput) {
                return
            }
            lastInput = newtInput
            debounceJob = uiScope.launch {
                delay(delayMillis)
                if (lastInput == newtInput) {
                    input(newtInput)
                }
            }
        }

        override fun beforeTextChanged(cs: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}