package com.serioussem.currentweather.domain.usecase


import com.serioussem.currentweather.domain.core.DomainResult
import com.serioussem.currentweather.domain.models.DomainWeatherModel
import com.serioussem.currentweather.domain.repository.WeatherRepository
import javax.inject.Inject

class FetchWeatherUseCase @Inject constructor(private val repository: WeatherRepository) {
    suspend fun fetchWeather(): MutableList<DomainResult<DomainWeatherModel?>> =
        repository.fetchWeather()
}