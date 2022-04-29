package com.serioussem.currentweather.data

import com.serioussem.currentweather.core.Abstract
import com.serioussem.currentweather.data.net.ServerModel

interface CloudMapper: Abstract.Mapper {

    fun map(city: String, temperature: Double) : WeatherModel

    class Base : CloudMapper {
        override fun map(city: String, temperature: Double) = WeatherModel(city, temperature)
    }

}