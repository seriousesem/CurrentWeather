package com.serioussem.currentweather.data.api



interface ApiDataSource {

    suspend fun fetchWeather(city: String): ApiResponseState

    class Base(
        private val apiService: ApiService,
        private val apiResponseHandler: ApiResponseHandler,

    ) :
        ApiDataSource {

        override suspend fun fetchWeather(city: String) =
            apiResponseHandler.handlerResponse {
                apiService.fetchWeather(city = city)
            }
    }
}
