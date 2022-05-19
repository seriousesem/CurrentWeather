package com.serioussem.currentweather.domain.core

interface BaseMapper<T, S> {
    fun map(data: T): S
}