package com.serioussem.currentweather.data.cloud

import javax.inject.Inject


interface CloudDataSource {

    suspend fun fetchWeather(city: String): ResponseResult

    class Base @Inject constructor(
        private val apiService: ApiService,
        private val responseHandler: ResponseHandler,

        ) :
        CloudDataSource {

        override suspend fun fetchWeather(city: String) =
            responseHandler.handlerResponse {
                apiService.fetchWeather(city = city)
            }
    }
}
