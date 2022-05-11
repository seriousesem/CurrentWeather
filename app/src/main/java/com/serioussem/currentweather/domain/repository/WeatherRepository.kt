package com.serioussem.currentweather.domain.repository


import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.domain.model.WeatherModel

interface WeatherRepository{

    suspend fun fetchWeather(city: String): ResultState<WeatherModel>

        fun fetchUserCity(): String

}