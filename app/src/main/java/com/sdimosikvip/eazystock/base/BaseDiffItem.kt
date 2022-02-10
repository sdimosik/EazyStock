package com.sdimosikvip.eazystock.base

abstract class BaseDiffItem(
    open val isFirstId: Boolean,
    open val firstId: Long = 0,
    open val secondId: String = "",
) {

    open fun isIdEqual(other: BaseDiffItem): Boolean =
        if (isFirstId) firstId == other.firstId else secondId == other.secondId
}