package com.serioussem.currentweather.domain.interactor


import com.serioussem.currentweather.domain.core.BaseResult
import com.serioussem.currentweather.domain.core.Failure
import com.serioussem.currentweather.domain.model.WeatherModel
import com.serioussem.currentweather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchWeatherInteractor @Inject constructor(private val repository: WeatherRepository) {
    suspend fun fetchWeather(city: String): BaseResult<WeatherModel, Failure> =
        repository.fetchWeather(city = city)
}