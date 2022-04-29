package com.serioussem.currentweather.data

import com.serioussem.currentweather.data.net.ApiService
import com.serioussem.currentweather.data.net.ServerModelMapper

interface CloudCacheDataSource {

    suspend fun fetchWeather(city: String): Double

    class Base(private val service: ApiService, private val serverModelMapper: ServerModelMapper) : CloudCacheDataSource {

        override suspend fun fetchWeather(city: String) =
            service.fetchWeather(city = city).map(serverModelMapper)

    }
}