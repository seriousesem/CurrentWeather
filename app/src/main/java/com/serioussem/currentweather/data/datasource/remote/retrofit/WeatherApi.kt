package com.serioussem.currentweather.data.datasource.remote.retrofit

import com.serioussem.currentweather.utils.Constants.APP_ID
import com.serioussem.currentweather.utils.Constants.LANG
import com.serioussem.currentweather.utils.Constants.UNITS
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("data/2.5/weather?")
    suspend fun fetchWeather(
        @Query("q") city: String,
        @Query("appid") app_id: String = APP_ID,
        @Query("lang") lang: String = LANG,
        @Query("units") units: String = UNITS
    ): Response<ApiModel>
}


