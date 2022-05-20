package com.serioussem.currentweather.domain.core


sealed class DomainResult<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Init<T> : DomainResult<T?>()
    class Loading<T> : DomainResult<T?>()
    class Success<T>(data: T?) : DomainResult<T?>(data = data)
    class Error<T>(message: String?) : DomainResult<T?>(message = message)
}