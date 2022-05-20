package com.serioussem.currentweather.data.datasource.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "weather",
    indices = [Index(value = ["city"], unique = true)]
)
data class DataWeatherModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "city")
    val city: String = "",
    @ColumnInfo(name = "temperature")
    val temperature: Double = 0.0
)