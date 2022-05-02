package com.serioussem.currentweather.data.mapper

import com.serioussem.currentweather.core.Abstract
import com.serioussem.currentweather.data.model.DataBaseModel


interface WeatherToDataBaseModelMapper : Abstract.Mapper {

    fun map(city: String, temperature: Double): DataBaseModel

    class Base : WeatherToDataBaseModelMapper {
        override fun map(city: String, temperature: Double) =
            DataBaseModel(city = city, temperature = temperature)

    }
}