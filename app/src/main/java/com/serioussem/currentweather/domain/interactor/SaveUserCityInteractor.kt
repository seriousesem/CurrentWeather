package com.serioussem.currentweather.domain.interactor

import com.serioussem.currentweather.data.model.CityModel
import com.serioussem.currentweather.domain.repository.WeatherRepository
import javax.inject.Inject

class SaveUserCityInteractor @Inject constructor(private val repository: WeatherRepository) {
    fun saveUserCity(city: CityModel) = repository.saveUserCity(city)
}