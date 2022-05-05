package com.serioussem.currentweather.domain.core

sealed class BaseResult<out T: Any, out E: Any> {

    data class Success<T: Any>(val data: T): BaseResult<T, Nothing>()
    data class Error<E: Any>(val error: E): BaseResult<Nothing, E>()
}