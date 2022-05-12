package com.serioussem.currentweather.data.cache


import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.domain.model.WeatherModel
import javax.inject.Inject


class CacheDataSource @Inject constructor(private val weatherDao: WeatherDao) {

    suspend fun fetchWeather(city: String): ResultState<WeatherModel> =
        ResultState.Success( weatherDao.fetchWeather(city = city))


    fun fetchCityList(): List<String> =
        weatherDao.fetchCityList()


    suspend fun saveWeather(weather: WeatherModel) =
        weatherDao.saveWeather(weather = weather)


}



