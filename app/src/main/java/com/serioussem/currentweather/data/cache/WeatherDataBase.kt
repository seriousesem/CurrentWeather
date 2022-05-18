package com.serioussem.currentweather.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.serioussem.currentweather.data.model.WeatherModel


@Database(
    entities = [WeatherModel::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}