package com.serioussem.currentweather.data.datasource.cache.room

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.serioussem.currentweather.data.datasource.models.DataModel


@Dao
interface WeatherDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveWeather(weather: DataModel)

    @Query("SELECT * FROM weather WHERE city = :city")
    suspend fun fetchWeather(city: String): DataModel

    @Query("SELECT city  FROM weather ORDER BY id")
    suspend fun fetchDataBaseCityList(): List<String>

}
