package com.serioussem.currentweather.domain.interactor

import com.serioussem.currentweather.domain.repository.WeatherRepository
import javax.inject.Inject

class UpdateUserCityInteractor @Inject constructor(private val repository: WeatherRepository) {
    fun updateUserCity(city: String) = repository.updateUserCity(city = city)

}