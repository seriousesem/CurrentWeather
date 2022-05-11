//package com.serioussem.currentweather.data.core
//
//import com.serioussem.currentweather.R
//import com.serioussem.currentweather.domain.core.ResultState
//import com.serioussem.currentweather.domain.model.WeatherModel
//import retrofit2.Response
//
//interface ResponseHandler {
//
//    suspend fun <T> handlerResponse(apiResponse: suspend () -> Response<T>): ResultState<WeatherModel>
//
//    class Base(
//        private val networkInterceptor: NetworkInterceptor,
//        private val resourceProvider: ResourceProvider
//    ) : ResponseHandler {
//        override suspend fun <T> handlerResponse(
//            apiResponse: suspend () -> Response<T>
//        ): ResultState<WeatherModel> {
//            if (networkInterceptor.isConnected()) {
//                try {
//                    val response = apiResponse()
//                    val body = response.body()
//
//                    if (response.isSuccessful && body != null) {
//                        return ResultState.Success(data = body)
//                    }
//                    return ResultState.Failure(
//                        message = resourceProvider.string(R.string.city_not_found))
//                } catch (failure: Exception) {
//                    return ResponseResult.Failure(
//                        message = resourceProvider.string(R.string.server_not_response))
//                }
//
//            } else {
//                return ResponseResult.InternetFailure(
//                    message = resourceProvider.string(R.string.no_internet_connection_message)
//                )
//            }
//        }
//
//    }
//}