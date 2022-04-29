package com.serioussem.currentweather.data

import com.serioussem.currentweather.core.Abstract
import com.serioussem.currentweather.domain.WeatherDomain
import retrofit2.HttpException
import java.net.UnknownHostException

interface DataToDomainMapper : Abstract.Mapper {

    fun map(weatherModel: WeatherModel): WeatherDomain

    fun map(error: Exception): WeatherDomain

    class Base : DataToDomainMapper {
        override fun map(weatherModel: WeatherModel): WeatherDomain =
            WeatherDomain.Success(weather = weatherModel)

        override fun map(error: Exception): WeatherDomain {
            val errorType = when (error) {
                is UnknownHostException -> 0 // enum class ErrorType -  NoConnection
                is HttpException -> 1 // ServiceUnavailable
                else -> 2 // GenericException
            }
            return WeatherDomain.Failure(errorType)
        }

    }

}

