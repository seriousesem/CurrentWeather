package com.serioussem.currentweather.data.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.serioussem.currentweather.domain.model.WeatherModel


@Dao
interface WeatherDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveWeather(weather: WeatherModel)

    @Query("SELECT * FROM weather WHERE city = :city")
    suspend fun fetchWeather(city: String): WeatherModel

    @Query("SELECT city  FROM weather ORDER BY id LIMIT 1")
    fun fetchUserCity(): String


}