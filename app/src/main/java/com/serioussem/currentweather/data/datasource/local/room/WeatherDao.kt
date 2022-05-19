package com.serioussem.currentweather.data.datasource.local.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE


@Dao
interface WeatherDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveWeather(weather: WeatherEntity)

    @Query("SELECT * FROM weather WHERE city = :city")
    suspend fun fetchWeather(city: String): WeatherEntity

    @Query("SELECT city  FROM weather ORDER BY id")
    suspend fun fetchDataBaseCityList(): List<String>

}
