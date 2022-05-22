package com.serioussem.currentweather.data.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.serioussem.currentweather.data.datasource.models.DataWeatherModel


@Database(
    entities = [DataWeatherModel::class],
    version = 1,
    exportSchema = false
)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}