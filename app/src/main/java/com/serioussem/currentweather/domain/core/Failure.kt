package com.serioussem.currentweather.domain.core

data class Failure(
    private val code: Int,
    private val message: String
)