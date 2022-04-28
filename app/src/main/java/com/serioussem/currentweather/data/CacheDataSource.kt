package com.serioussem.currentweather.data

import com.serioussem.currentweather.data.net.ServerModel
import com.serioussem.currentweather.data.net.ApiService

interface CacheDataSource {

    suspend fun fetchWeather(city: String): ServerModel

    class Base(private val service: ApiService) : CacheDataSource {

        override suspend fun fetchWeather(city: String): ServerModel =
            service.fetchWeather(city = city)

    }
}