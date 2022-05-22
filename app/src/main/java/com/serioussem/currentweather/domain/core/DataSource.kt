package com.serioussem.currentweather.domain.core


interface DataSource<T, S>{
    suspend fun fetchWeather(city: T): S
}
