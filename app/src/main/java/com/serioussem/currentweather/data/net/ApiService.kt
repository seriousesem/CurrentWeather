package com.serioussem.currentweather.data.net

import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("&")
    suspend fun fetchWeather(
       @Query ("q") city: String
    ): ServerModel
}


