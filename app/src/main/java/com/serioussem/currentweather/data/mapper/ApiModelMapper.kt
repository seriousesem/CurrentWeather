package com.serioussem.currentweather.data.mapper

import com.serioussem.currentweather.core.Abstract
import javax.inject.Inject

interface ApiModelMapper: Abstract.Mapper{

    fun map(temperature: Double): Double

    class Base @Inject constructor() : ApiModelMapper {
        override fun map(temperature: Double) = temperature
    }

}

