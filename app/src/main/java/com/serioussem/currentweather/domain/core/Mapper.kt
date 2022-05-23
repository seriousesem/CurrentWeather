package com.serioussem.currentweather.domain.core

interface Mapper<T, S> {
    fun map(params: T): S
}