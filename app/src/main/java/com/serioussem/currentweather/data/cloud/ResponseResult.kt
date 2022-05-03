package com.serioussem.currentweather.data.cloud

sealed class ResponseResult {

    data class  Success<T>(val data: T): ResponseResult()
    data class Failure(val message: String): ResponseResult()
    data class InternetFailure(val message : String): ResponseResult()
}