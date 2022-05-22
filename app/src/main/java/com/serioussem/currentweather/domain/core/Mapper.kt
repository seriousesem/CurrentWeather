package com.serioussem.currentweather.domain.core

interface Mapper<T, S> {
    fun map(data: T): S
}