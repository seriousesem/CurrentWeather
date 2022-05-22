package com.serioussem.currentweather.data.core


sealed class DataResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Init<T> : DataResult<T?>()
    class Success<T>(data: T?) : DataResult<T?>(data = data)
    class Error<T>(message: String?) : DataResult<T?>(message = message)
}