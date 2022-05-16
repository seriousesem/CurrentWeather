package com.serioussem.currentweather.domain.interactor


import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.domain.model.WeatherModel
import com.serioussem.currentweather.domain.repository.WeatherRepository
import javax.inject.Inject

class FetchWeatherInteractor @Inject constructor(private val repository: WeatherRepository) {
    suspend fun fetchWeather(): MutableList<ResultState<WeatherModel>> =
        repository.fetchWeather()
}