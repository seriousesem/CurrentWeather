package com.serioussem.currentweather.data.datasource.local.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.serioussem.currentweather.data.datasource.models.DataWeatherModel


@Dao
interface WeatherDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveWeather(weather: DataWeatherModel)

    @Query("SELECT * FROM weather WHERE city = :city")
    suspend fun fetchWeather(city: String): DataWeatherModel

    @Query("SELECT city  FROM weather ORDER BY id")
    suspend fun fetchDataBaseCityList(): List<String>

}
