package com.serioussem.currentweather.data

interface WeatherRepository {

    suspend fun fetchTemperature(city: String): WeatherDataModel

    class Base(private val weatherCacheDataSource: WeatherCacheDataSource) : WeatherRepository {

        override suspend fun fetchTemperature(city: String) = try {
            WeatherDataModel.Success(weatherCacheDataSource.fetchWeather(city = city))
        } catch (e: Exception) {
            WeatherDataModel.Failure(e)
        }
    }
}