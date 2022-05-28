package com.serioussem.currentweather.data.core

interface DataSource<T, S> {

    suspend fun fetchWeather(city: T): S

}