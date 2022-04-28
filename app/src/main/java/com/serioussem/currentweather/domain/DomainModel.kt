package com.serioussem.currentweather.domain

import com.serioussem.currentweather.core.Abstract
import com.serioussem.currentweather.data.net.ServerModel
import com.serioussem.currentweather.presentation.WeatherUiModel

sealed class DomainModel : Abstract.Object<WeatherUiModel, Abstract.Mapper.Empty>() {

    class Success(private val temperature: ServerModel): DomainModel(){
        override fun map(mapper: Abstract.Mapper.Empty): WeatherUiModel {
            TODO("Not yet implemented")
        }
    }

    class Failure(private val errorType: Int): DomainModel() {
        override fun map(mapper: Abstract.Mapper.Empty): WeatherUiModel {
            TODO("Not yet implemented")
        }

    }

}