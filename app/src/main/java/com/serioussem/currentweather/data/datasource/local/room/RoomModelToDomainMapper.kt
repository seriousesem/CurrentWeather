package com.serioussem.currentweather.data.datasource.local.room


import com.serioussem.currentweather.domain.core.BaseMapper
import com.serioussem.currentweather.domain.models.WeatherModel

class RoomModelToDomainMapper: BaseMapper<WeatherEntity, WeatherModel> {
    override fun map(data: WeatherEntity): WeatherModel =
        WeatherModel(city = data.city, temperature = data.temperature)
}