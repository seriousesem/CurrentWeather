package com.serioussem.currentweather.data.model

import com.serioussem.currentweather.core.Abstract
import com.serioussem.currentweather.data.mapper.DataToDomainMapper
import com.serioussem.currentweather.domain.WeatherDomain

sealed class WeatherData : Abstract.Object<WeatherDomain, DataToDomainMapper>() {

    class Success(private val weatherModel: WeatherModel) : WeatherData() {
        override fun map(mapper: DataToDomainMapper): WeatherDomain =
            mapper.map(weatherModel = weatherModel)
    }

    class Failure(private val failure: String) : WeatherData() {
        override fun map(mapper: DataToDomainMapper): WeatherDomain =
            mapper.map(failure = failure)
    }
}