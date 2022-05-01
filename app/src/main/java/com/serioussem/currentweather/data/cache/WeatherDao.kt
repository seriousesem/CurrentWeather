package com.serioussem.currentweather.data.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.serioussem.currentweather.data.model.DataBaseModel

@Dao
interface WeatherDao {

    @Insert(onConflict = REPLACE)
    suspend fun saveWeather(dataBaseModel: DataBaseModel)

    @Query("SELECT * FROM weather WHERE city = :city")
    suspend fun fetchWeather(city: String): DataBaseModel

}