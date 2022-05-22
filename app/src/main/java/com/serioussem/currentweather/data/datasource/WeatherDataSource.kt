package com.serioussem.currentweather.data.datasource

import com.serioussem.currentweather.data.core.DataResult
import com.serioussem.currentweather.data.datasource.models.DataWeatherModel

interface WeatherDataSource{
    suspend fun fetchWeather(city: String): DataResult<DataWeatherModel?>
}
