package com.serioussem.currentweather.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.serioussem.currentweather.data.model.DataBaseModel

@Database(
    entities = [DataBaseModel::class],
    version = 1
)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}