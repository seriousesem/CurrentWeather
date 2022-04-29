package com.serioussem.currentweather.data

interface WeatherRepository {

    suspend fun fetchWeather(city: String): WeatherData

    class Base(private val cloudCacheDataSource: CloudCacheDataSource,
               private val cloudMapper: CloudMapper) : WeatherRepository {

        override suspend fun fetchWeather(city: String) = try {
            val temperature: Double= cloudCacheDataSource.fetchWeather(city = city)
            WeatherData.Success(cloudMapper.map(city = city, temperature = temperature))
        } catch (e: Exception) {
            WeatherData.Failure(e)
        }
    }
}