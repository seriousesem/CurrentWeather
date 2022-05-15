package com.serioussem.currentweather.data.cache

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.serioussem.currentweather.domain.model.WeatherModel


@Dao
interface WeatherDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveWeather(weather: WeatherModel)

    @Update
    suspend fun updateWeather(weather: WeatherModel)

    @Query("SELECT * FROM weather WHERE city = :city")
    suspend fun fetchWeather(city: String): WeatherModel

    @Query("SELECT city  FROM weather ")
    fun fetchCityList(): List<String>

}
//WHERE (id = 1 AND id = 2) ORDER BY id AND id IN (SELECT city FROM weather ORDER BY id DESC LIMIT 1)