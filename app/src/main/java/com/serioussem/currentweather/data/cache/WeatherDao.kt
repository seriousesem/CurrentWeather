package com.serioussem.currentweather.data.cache

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.serioussem.currentweather.domain.model.WeatherModel


@Dao
interface WeatherDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveWeather(weather: WeatherModel)

    @Query("SELECT * FROM weather WHERE city = :city")
    suspend fun fetchWeather(city: String): WeatherModel

    @Query("SELECT city  FROM weather ORDER BY id")
    fun fetchDataBaseCityList(): List<String>

}
