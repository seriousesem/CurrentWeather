package com.serioussem.currentweather.domain

import com.serioussem.currentweather.core.Abstract
import com.serioussem.currentweather.presentation.WeatherUiModel

sealed class WeatherDomainModel : Abstract.Object<WeatherUiModel, Abstract.Mapper.Empty>() {

}