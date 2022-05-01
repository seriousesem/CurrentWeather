package com.serioussem.currentweather.data.cache

import android.content.Context
import androidx.room.Room
import com.serioussem.currentweather.data.model.DataBaseModel
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock


interface CacheDataSource {

    suspend fun fetchWeather(city: String): DataBaseModel

    suspend fun saveWeather (dataBaseModel: DataBaseModel)

    abstract class Abstract(
        context: Context,
        dataBaseName: String
    ) : CacheDataSource {

        private val room = Room.databaseBuilder(
            context.applicationContext,
            WeatherDataBase::class.java,
            dataBaseName
        ).build()

        companion object {
            private val mutex = Mutex()
        }

        override suspend fun fetchWeather(city: String): DataBaseModel {
            return mutex.withLock {
                room.weatherDao().fetchWeather(city = city)
            }
        }

        override suspend fun saveWeather(dataBaseModel: DataBaseModel) {
             mutex.withLock {
                 room.weatherDao().saveWeather(dataBaseModel)
             }
        }
    }

    class Base(context: Context) : Abstract(
        context = context.applicationContext,
        dataBaseName = "weather.db"
    )
}


