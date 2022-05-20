package com.serioussem.currentweather.domain.repository


import com.serioussem.currentweather.domain.core.DomainResult
import com.serioussem.currentweather.domain.models.DomainWeatherModel

interface WeatherRepository{

    suspend fun fetchWeather(): MutableList<DomainResult<DomainWeatherModel?>>

    fun saveUserCity(city: String)

}