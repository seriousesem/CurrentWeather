package com.serioussem.currentweather.domain.interactor

import com.serioussem.currentweather.domain.repository.WeatherRepository
import javax.inject.Inject

class FetchUserCityInteractor @Inject constructor(private val repository: WeatherRepository) {
           fun fetchUserCity(): List<String> = repository.fetchCityList()
}