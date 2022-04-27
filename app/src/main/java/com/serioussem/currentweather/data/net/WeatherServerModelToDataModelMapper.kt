package com.serioussem.currentweather.data.net

import com.serioussem.currentweather.core.Abstract
import com.serioussem.currentweather.data.WeatherDataModel

interface WeatherServerModelToDataModelMapper: Abstract.Mapper{

    fun map(temperature: Double): WeatherDataModel

}

