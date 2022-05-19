package com.serioussem.currentweather.data.datasource.local.room

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = false
)
abstract class WeatherDataBase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}