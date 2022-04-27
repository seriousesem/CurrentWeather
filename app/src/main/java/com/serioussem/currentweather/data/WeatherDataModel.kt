package com.serioussem.currentweather.data

import com.serioussem.currentweather.core.Abstract
import com.serioussem.currentweather.domain.WeatherDomainModel

sealed class WeatherDataModel : Abstract.Object<WeatherDomainModel, Abstract.Mapper.Empty>() {

}