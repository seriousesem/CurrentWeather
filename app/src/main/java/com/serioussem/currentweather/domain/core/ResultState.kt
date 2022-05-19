package com.serioussem.currentweather.domain.core


sealed class ResultState<T>(
    val data: T? = null,
    val message: String? = null,
    val empty: Int? = null
) {
    class Init<T>(emptyState: Int? = null) : ResultState<T>(empty = emptyState)
    class Loading<T> : ResultState<T>()
    class Success<T>(data: T) : ResultState<T>(data = data)
    class Error<T>(message: String) : ResultState<T>(message = message)
}