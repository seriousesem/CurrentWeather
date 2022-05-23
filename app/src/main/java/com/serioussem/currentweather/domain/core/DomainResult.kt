package com.serioussem.currentweather.domain.core


sealed class DomainResult<T>(
    val data: T? = null,
    val message: String? = null,
    val empty: Int? = null
) {
    class Init<T>(emptyState: Int? = null) : DomainResult<T>(empty = emptyState)
    class Loading<T> : DomainResult<T>()
    class Success<T>(data: T) : DomainResult<T>(data = data)
    class Error<T>(message: String, data: T? = null) : DomainResult<T>(data = data, message = message)
}