package com.serioussem.currentweather.data.cloud

import retrofit2.Response

interface ResponseHandler {

    suspend fun <T> handlerResponse(apiResponse: suspend () -> Response<T>): ResponseResult

    class Base(private val connection: Connection) : ResponseHandler {
        override suspend fun <T> handlerResponse(
            apiResponse: suspend () -> Response<T>
        ): ResponseResult {
            if (connection.isNetworkConnected()) {
                try {
                    val response = apiResponse()
                    val body = response.body()

                    if (response.isSuccessful && body != null) {
                        return ResponseResult.Success(data = body)
                    }
                    return ResponseResult.Failure(message = response.errorBody().toString())
                } catch (failure: Exception) {
                    return ResponseResult.Failure(message = failure.message.toString())
                }

            } else {
                return ResponseResult.InternetFailure(message = "no internet")
            }
        }

    }
}