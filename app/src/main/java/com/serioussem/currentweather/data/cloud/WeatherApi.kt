package com.serioussem.currentweather.data.cloud

import com.serioussem.currentweather.data.model.ApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather?")
    suspend fun fetchWeather(
        @Query("q") city: String,
        @Query("appid") app_id: String = "df407ee3089050448a58024e26abac06",
        @Query("lang") lang: String = "uk",
        @Query("units") units: String = "metric"
    ): Response<ApiModel>
}


