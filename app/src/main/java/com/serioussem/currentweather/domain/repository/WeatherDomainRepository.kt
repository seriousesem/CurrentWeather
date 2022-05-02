package com.serioussem.currentweather.domain.repository

import com.serioussem.currentweather.data.model.WeatherData
import com.serioussem.currentweather.data.model.WeatherModel

interface WeatherDomainRepository {

    suspend fun fetchWeather(city: String): WeatherData

    suspend fun saveWeather(weatherModel: WeatherModel)
}