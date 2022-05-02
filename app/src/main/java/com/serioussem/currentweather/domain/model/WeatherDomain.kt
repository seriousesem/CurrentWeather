package com.serioussem.currentweather.domain.model

import com.serioussem.currentweather.core.Abstract
import com.serioussem.currentweather.data.model.WeatherModel
import com.serioussem.currentweather.presentation.WeatherUiModel

sealed class WeatherDomain : Abstract.Object<WeatherUiModel, Abstract.Mapper.Empty>() {

    class Success(private val weatherModel: WeatherModel): WeatherDomain(){
        override fun map(mapper: Abstract.Mapper.Empty): WeatherUiModel {
            TODO("Not yet implemented")
        }
    }

    class Failure(private val failure: String): WeatherDomain() {
        override fun map(mapper: Abstract.Mapper.Empty): WeatherUiModel {
            TODO("Not yet implemented")
        }

    }

}