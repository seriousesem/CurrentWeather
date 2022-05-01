package com.serioussem.currentweather.data.api

import com.serioussem.currentweather.core.Connection
import com.serioussem.currentweather.data.model.ApiModel
import retrofit2.Response

interface ApiResponseHandler {

    suspend fun <T> handlerResponse(apiResponse: suspend () -> Response<T>): ApiResponseState

    class Base(private val connection: Connection) : ApiResponseHandler {
        override suspend fun <T> handlerResponse(
            apiResponse: suspend () -> Response<T>
        ): ApiResponseState {
            if (connection.isNetworkConnected()) {
                try {
                    val response = apiResponse()
                    val body = response.body()

                    if (response.isSuccessful && body != null) {
                        return ApiResponseState.Success(data = body)
                    }
                    return ApiResponseState.ApiFailure(message = response.errorBody().toString())
                } catch (failure: Exception) {
                    return ApiResponseState.ApiFailure(message = failure.message.toString())
                }

            } else {
                return ApiResponseState.InternetFailure(message = "no internet")
            }
        }

    }
}