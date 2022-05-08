package com.serioussem.currentweather.data.cache


import com.serioussem.currentweather.domain.model.WeatherModel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject


class CacheDataSource @Inject constructor(private val weatherDao: WeatherDao) {

    companion object {
        private val mutex = Mutex()
    }

    suspend fun fetchWeather(city: String): WeatherModel {
        return mutex.withLock {
            weatherDao.fetchWeather(city = city)
        }
    }

    suspend fun fetchCityList():MutableList<String>{
        return mutex.withLock {
            weatherDao.fetchCityList()
        }
    }

    suspend fun saveWeather(weather: WeatherModel) {
        mutex.withLock {
            weatherDao.saveWeather(weather = weather)
        }
    }
}



