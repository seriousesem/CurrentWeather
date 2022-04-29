package com.serioussem.currentweather.data.net

import com.serioussem.currentweather.core.Abstract

interface ServerModelMapper: Abstract.Mapper{

    fun map(temperature: Double): Double

    class Base : ServerModelMapper{
        override fun map(temperature: Double) = temperature
    }

}

