package com.serioussem.currentweather.domain.model


data class WeatherModel(
    private val city: String = "",
    private val temperature: Double = 0.0
)