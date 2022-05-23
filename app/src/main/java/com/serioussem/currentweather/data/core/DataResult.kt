package com.serioussem.currentweather.data.core

sealed class DataResult<T>(
    val data: T? = null,
    val message: String? = null,
    val empty: Int? = null
) {
    class Init<T>(emptyState: Int? = null) : DataResult<T>(empty = emptyState)
    class Loading<T> : DataResult<T>()
    class Success<T>(data: T) : DataResult<T>(data = data)
    class Error<T>(message: String, data: T? = null) : DataResult<T>(data = data, message = message)
}