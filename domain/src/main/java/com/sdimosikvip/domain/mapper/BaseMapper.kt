package com.sdimosikvip.domain.mapper

interface BaseMapper<R, D> {

    fun transformToDomain(type: R): D
}