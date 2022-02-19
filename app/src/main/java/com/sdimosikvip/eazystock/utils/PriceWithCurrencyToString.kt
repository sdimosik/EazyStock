package com.sdimosikvip.eazystock.utils

import com.sdimosikvip.eazystock.utils.extensions.round
import java.util.*
import kotlin.math.abs

fun priceWithCurrencyToString(price: Double, currency: String): String {
    return "${getSymbol(currency)}${price.round(2)}"
}

fun getSymbol(currency: String): String {
    return Currency.getInstance(currency).getSymbol(Locale.getDefault())
}

fun deltaWithPercentToString(delta: Double, percent: Double, currency: String): String {
    return if (delta >= 0) {
        "+${getSymbol(currency)}${abs(delta.round(2))} (${abs(percent.round(2))}%)"
    } else {
        "-${getSymbol(currency)}${abs(delta.round(2))} (${abs(percent.round(2))}%)"
    }
}