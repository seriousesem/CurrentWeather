package com.serioussem.currentweather.domain.usecase

import com.serioussem.currentweather.domain.repository.WeatherRepository
import javax.inject.Inject

class SaveUserCityUseCase @Inject constructor(private val repository: WeatherRepository) {
    fun saveUserCity(city: String) = repository.saveUserCity(city)
}