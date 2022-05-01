package com.serioussem.currentweather.data.api

sealed class ApiResponseState {

    data class  Success<T>(val data: T): ApiResponseState()
    data class ApiFailure(val message: String): ApiResponseState()
    data class InternetFailure(val message : String): ApiResponseState()
}