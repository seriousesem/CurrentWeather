package com.serioussem.currentweather.domain.repository


import com.serioussem.currentweather.domain.core.DomainResult
import com.serioussem.currentweather.domain.models.DomainModel

interface WeatherRepository{

    suspend fun fetchWeather(): MutableList<DomainResult<DomainModel>>

    fun saveUserCity(city: String)

}