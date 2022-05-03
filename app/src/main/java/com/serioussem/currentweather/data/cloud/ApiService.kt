package com.serioussem.currentweather.data.cloud

import com.serioussem.currentweather.data.model.ApiModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("&")
    suspend fun fetchWeather(
       @Query ("q") city: String
    ): Response<ApiModel>
}


