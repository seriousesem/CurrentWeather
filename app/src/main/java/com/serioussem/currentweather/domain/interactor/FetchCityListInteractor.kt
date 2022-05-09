package com.serioussem.currentweather.domain.interactor

import com.serioussem.currentweather.domain.repository.WeatherRepository
import javax.inject.Inject

class FetchCityListInteractor @Inject constructor(private val repository: WeatherRepository){
        suspend fun fetchCityList() = repository.fetchCityList()

}