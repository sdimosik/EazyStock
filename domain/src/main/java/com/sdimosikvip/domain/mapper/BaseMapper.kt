package com.sdimosikvip.domain.mapper

interface BaseMapper<R, D> {

    fun transform(o: R): D

    fun reverseTransform(o: D): R
}