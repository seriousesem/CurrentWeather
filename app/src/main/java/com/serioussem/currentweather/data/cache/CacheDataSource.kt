package com.serioussem.currentweather.data.cache


import com.serioussem.currentweather.data.model.DataBaseModel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject


interface CacheDataSource {

    suspend fun fetchWeather(city: String): DataBaseModel

    suspend fun saveWeather (dataBaseModel: DataBaseModel)

    abstract class Abstract (
        private val weatherDao: WeatherDao
    ) : CacheDataSource {

        companion object {
            private val mutex = Mutex()
        }

        override suspend fun fetchWeather(city: String): DataBaseModel {
            return mutex.withLock {
                weatherDao.fetchWeather(city = city)
            }
        }
//
//        override suspend fun saveWeather(dataBaseModel: DataBaseModel) {
//             mutex.withLock {
//                 weatherDao.saveWeather(dataBaseModel = dataBaseModel)
//             }
//        }
    }
}


