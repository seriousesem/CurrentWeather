package com.serioussem.currentweather.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "weather",
    indices = [Index(value = ["city"], unique = true)]
)
data class WeatherModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "city")
    val city: String = "",
    @ColumnInfo(name = "temperature")
    val temperature: Double = 0.0
)