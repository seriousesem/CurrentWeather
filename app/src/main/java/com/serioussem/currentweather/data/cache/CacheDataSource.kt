package com.serioussem.currentweather.data.cache


import com.serioussem.currentweather.domain.model.WeatherModel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject


class CacheDataSource @Inject constructor(private val weatherDao: WeatherDao) {

    suspend fun fetchWeather(city: String): WeatherModel =
        weatherDao.fetchWeather(city = city)


    suspend fun fetchCityList(): MutableList<String> =
        weatherDao.fetchCityList()


    suspend fun saveWeather(weather: WeatherModel) =
        weatherDao.saveWeather(weather = weather)

    suspend fun clearTable() = weatherDao.clearTable()

}



