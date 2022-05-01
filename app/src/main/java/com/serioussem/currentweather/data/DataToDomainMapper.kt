package com.serioussem.currentweather.data

import com.serioussem.currentweather.core.Abstract
import com.serioussem.currentweather.data.model.WeatherModel
import com.serioussem.currentweather.domain.WeatherDomain
import retrofit2.HttpException
import java.net.UnknownHostException

interface DataToDomainMapper : Abstract.Mapper {

    fun map(weatherModel: WeatherModel): WeatherDomain

    fun map(failure: String): WeatherDomain

    class Base : DataToDomainMapper {
        override fun map(weatherModel: WeatherModel): WeatherDomain =
            WeatherDomain.Success(weatherModel = weatherModel)

        override fun map(failure: String): WeatherDomain {


            return WeatherDomain.Failure(failure = failure)
        }

    }

}

