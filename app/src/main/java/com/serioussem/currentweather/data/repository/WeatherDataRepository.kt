package com.serioussem.currentweather.data.repository

import com.serioussem.currentweather.data.model.WeatherData
import com.serioussem.currentweather.data.cache.CacheDataSource
import com.serioussem.currentweather.data.cloud.CloudDataSource
import com.serioussem.currentweather.data.core.ResponseResult
import com.serioussem.currentweather.data.mapper.ApiModelMapper
import com.serioussem.currentweather.data.mapper.WeatherModelMapper
import com.serioussem.currentweather.data.mapper.WeatherToDataBaseModelMapper
import com.serioussem.currentweather.data.model.ApiModel
import com.serioussem.currentweather.data.model.WeatherModel
import com.serioussem.currentweather.domain.repository.WeatherRepository

interface WeatherDataRepository: WeatherRepository {

    override suspend fun fetchWeather(city: String): Flow<BaseResult<WeatherModel, Failure>>

    override suspend fun saveWeather(weatherModel: WeatherModel)

    class Base(
        private val cloudDataSource: CloudDataSource,
        private val cacheDataSource: CacheDataSource,
        private val weatherModelMapper: WeatherModelMapper,
        private val apiModelMapper: ApiModelMapper,
        private val weatherToDataBaseModelMapper: WeatherToDataBaseModelMapper
    ) : WeatherDataRepository {

        override suspend fun fetchWeather(city: String): Flow<BaseResult<WeatherModel, Failure>> {

            val apiResponse = cloudDataSource.fetchWeather(city = city)
            val weatherCache = cacheDataSource.fetchWeather(city = city).map(weatherModelMapper)

            return when (apiResponse) {
                is ResponseResult.Success<*> -> {
                    WeatherData.Success(
                        weatherModelMapper.map(
                            city = city,
                            temperature = (apiResponse.data as ApiModel).map(apiModelMapper)
                        )
                    )
                }
                is ResponseResult.InternetFailure -> {
                    WeatherData.Success(weatherCache)
                    WeatherData.Failure(apiResponse.message)
                }
                is ResponseResult.Failure -> {
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