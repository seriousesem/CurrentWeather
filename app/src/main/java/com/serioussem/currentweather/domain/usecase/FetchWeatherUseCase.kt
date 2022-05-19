package com.serioussem.currentweather.domain.usecase


import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.data.datasource.local.room.WeatherEntity
import com.serioussem.currentweather.domain.models.WeatherModel
import com.serioussem.currentweather.domain.repository.WeatherRepository
import javax.inject.Inject

class FetchWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend fun fetchWeather(): MutableList<ResultState<WeatherModel>> =
        repository.fetchWeather()
}