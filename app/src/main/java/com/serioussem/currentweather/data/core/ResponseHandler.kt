package com.serioussem.currentweather.data.core

import com.serioussem.currentweather.R
import retrofit2.Response

interface ResponseHandler {

    suspend fun <T> handlerResponse(apiResponse: suspend () -> Response<T>): ResponseResult

    class Base(
        private val networkInterceptor: NetworkInterceptor,
        private val resourceProvider: ResourceProvider
    ) : ResponseHandler {
        override suspend fun <T> handlerResponse(
            apiResponse: suspend () -> Response<T>
        ): ResponseResult {
            if (networkInterceptor.isConnected()) {
                try {
                    val response = apiResponse()
                    val body = response.body()

                    if (response.isSuccessful && body != null) {
                        return ResponseResult.Success(data = body)
                    }
                    return ResponseResult.Failure(
                        message = resourceProvider.string(R.string.server_not_response))
                } catch (failure: Exception) {
                    return ResponseResult.Failure(
                        message = resourceProvider.string(R.string.other_error))
                }

            } else {
                return ResponseResult.InternetFailure(
                    message = resourceProvider.string(R.string.no_internet_connection_message)
                )
            }
        }

    }
}