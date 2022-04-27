package com.serioussem.currentweather.data

import com.serioussem.currentweather.data.net.WeatherServerModel
import com.serioussem.currentweather.data.net.WeatherService

interface WeatherCacheDataSource {

    suspend fun fetchWeather(city: String): WeatherServerModel

    class Base(private val service: WeatherService) : WeatherCacheDataSource {

        override suspend fun fetchWeather(city: String): WeatherServerModel {
            return service.fetchWeather(city = city)
        }

    }
}