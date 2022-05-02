package com.serioussem.currentweather.data.repository

import com.serioussem.currentweather.data.model.WeatherData
import com.serioussem.currentweather.data.cache.CacheDataSource
import com.serioussem.currentweather.data.api.ApiDataSource
import com.serioussem.currentweather.data.api.ApiResponseState
import com.serioussem.currentweather.data.mapper.ApiModelMapper
import com.serioussem.currentweather.data.mapper.WeatherModelMapper
import com.serioussem.currentweather.data.mapper.WeatherToDataBaseModelMapper
import com.serioussem.currentweather.data.model.ApiModel
import com.serioussem.currentweather.data.model.WeatherModel
import com.serioussem.currentweather.domain.repository.WeatherDomainRepository

interface WeatherDataRepository: WeatherDomainRepository {

    override suspend fun fetchWeather(city: String): WeatherData

    override suspend fun saveWeather(weatherModel: WeatherModel)

    class Base(
        private val apiDataSource: ApiDataSource,
        private val cacheDataSource: CacheDataSource,
        private val weatherModelMapper: WeatherModelMapper,
        private val apiModelMapper: ApiModelMapper,
        private val weatherToDataBaseModelMapper: WeatherToDataBaseModelMapper
    ) : WeatherDataRepository {

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

        override suspend fun saveWeather(weatherModel: WeatherModel) {
            cacheDataSource.saveWeather(weatherModel.map(weatherToDataBaseModelMapper))
        }
    }
}