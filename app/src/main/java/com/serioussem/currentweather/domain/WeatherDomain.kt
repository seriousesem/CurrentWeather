package com.serioussem.currentweather.domain

import com.serioussem.currentweather.core.Abstract
import com.serioussem.currentweather.data.WeatherModel
import com.serioussem.currentweather.presentation.WeatherUiModel

sealed class WeatherDomain : Abstract.Object<WeatherUiModel, Abstract.Mapper.Empty>() {

    class Success(private val weatherModel: WeatherModel): WeatherDomain(){
        override fun map(mapper: Abstract.Mapper.Empty): WeatherUiModel {
            TODO("Not yet implemented")
        }
    }

    class Failure(private val errorType: Int): WeatherDomain() {
        override fun map(mapper: Abstract.Mapper.Empty): WeatherUiModel {
            TODO("Not yet implemented")
        }

    }

}