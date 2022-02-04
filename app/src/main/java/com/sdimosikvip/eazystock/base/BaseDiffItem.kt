package com.sdimosikvip.eazystock.base

abstract class BaseDiffItem(
    open val id: Long = 0
) {

    open fun isIdEqual(other: BaseDiffItem): Boolean = id == other.id
}