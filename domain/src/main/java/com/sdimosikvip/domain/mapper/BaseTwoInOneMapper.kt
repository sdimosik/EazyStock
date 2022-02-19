package com.sdimosikvip.domain.mapper

interface BaseTwoInOneMapper<I1, I2, O> {

    fun transform(typeI1: I1, typeI2: I2): O
}