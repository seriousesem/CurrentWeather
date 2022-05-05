package com.serioussem.currentweather.domain.interactor

import com.serioussem.currentweather.domain.core.BaseResult
import com.serioussem.currentweather.domain.core.Failure
import com.serioussem.currentweather.domain.model.WeatherModel
import com.serioussem.currentweather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchWeatherInteractor @Inject constructor(private val weatherRepository: WeatherRepository) {
    suspend fun execute(city: String): Flow<BaseResult<WeatherModel, Failure>> =
        weatherRepository.fetchWeather(city = city)
}