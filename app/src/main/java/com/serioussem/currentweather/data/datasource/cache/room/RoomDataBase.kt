package com.serioussem.currentweather.data.datasource.cache.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.serioussem.currentweather.data.datasource.models.DataModel


@Database(
    entities = [DataModel::class],
    version = 1,
    exportSchema = false
)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}