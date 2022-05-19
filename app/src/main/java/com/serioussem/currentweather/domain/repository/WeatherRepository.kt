package com.serioussem.currentweather.domain.repository


import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.domain.models.WeatherModel

interface WeatherRepository{

    suspend fun fetchWeather(): MutableList<ResultState<WeatherModel>>

    fun saveUserCity(cityModel: String)

}