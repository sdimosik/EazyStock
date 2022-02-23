package com.sdimosikvip.domain.mapper

interface BaseMapper<R, D> {

    fun transform(type: R): D
}