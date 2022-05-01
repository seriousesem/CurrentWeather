package com.serioussem.currentweather.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.serioussem.currentweather.core.Abstract
import com.serioussem.currentweather.data.mapper.WeatherModelMapper

@Entity(tableName = "weather")
data class DataBaseModel(
    @PrimaryKey
    @ColumnInfo(name = "city")
    private val city: String,
    @ColumnInfo(name = "temperature")
    private val temperature: Double
) : Abstract.Object<WeatherModel, WeatherModelMapper>() {

    override fun map(mapper: WeatherModelMapper) = WeatherModel(city, temperature)
}