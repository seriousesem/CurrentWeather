package com.serioussem.currentweather.data.mapper

import com.serioussem.currentweather.core.Abstract

interface ApiModelMapper: Abstract.Mapper{

    fun map(temperature: Double): Double

    class Base : ApiModelMapper {
        override fun map(temperature: Double) = temperature
    }

}

