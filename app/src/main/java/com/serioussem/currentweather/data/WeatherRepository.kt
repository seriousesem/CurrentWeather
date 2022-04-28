package com.serioussem.currentweather.data

interface WeatherRepository {

    suspend fun fetchTemperature(city: String): DataModel

    class Base(private val cacheDataSource: CacheDataSource) : WeatherRepository {

        override suspend fun fetchTemperature(city: String) = try {
            DataModel.Success(cacheDataSource.fetchWeather(city = city))
        } catch (e: Exception) {
            DataModel.Failure(e)
        }
    }
}