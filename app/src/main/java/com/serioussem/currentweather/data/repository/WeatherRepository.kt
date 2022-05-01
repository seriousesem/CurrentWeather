package com.serioussem.currentweather.data.repository

import com.serioussem.currentweather.data.WeatherData
import com.serioussem.currentweather.data.cache.CacheDataSource
import com.serioussem.currentweather.data.api.ApiDataSource
import com.serioussem.currentweather.data.api.ApiResponseState
import com.serioussem.currentweather.data.mapper.ApiModelMapper
import com.serioussem.currentweather.data.mapper.WeatherModelMapper
import com.serioussem.currentweather.data.model.ApiModel

interface WeatherRepository {

    suspend fun fetchWeather(city: String): WeatherData

    class Base(
        private val apiDataSource: ApiDataSource,
        private val cacheDataSource: CacheDataSource,
        private val weatherModelMapper: WeatherModelMapper,
        private val apiModelMapper: ApiModelMapper,
    ) : WeatherRepository {

        override suspend fun fetchWeather(city: String): WeatherData {

            val apiResponse = apiDataSource.fetchWeather(city = city)
            val weatherCache = cacheDataSource.fetchWeather(city = city).map(weatherModelMapper)

            return when (apiResponse) {
                is ApiResponseState.Success<*> -> {
                    WeatherData.Success(
                        weatherModelMapper.map(
                            city = city,
                            temperature = (apiResponse.data as ApiModel).map(apiModelMapper)
                        )
                    )
                }
                is ApiResponseState.InternetFailure -> {
                    WeatherData.Success(weatherCache)
                    WeatherData.Failure(apiResponse.message)
                }

                is ApiResponseState.ApiFailure -> {
                    WeatherData.Success(weatherCache)
                    WeatherData.Failure(apiResponse.message)
                }
            }
        }
    }
}