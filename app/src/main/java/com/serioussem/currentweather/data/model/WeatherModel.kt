//package com.serioussem.currentweather.data.model
//
//import com.serioussem.currentweather.core.Abstract
//import com.serioussem.currentweather.data.mapper.WeatherToDataBaseModelMapper
//
//data class WeatherModel(
//    private val city: String,
//    private val temperature: Double
//) : Abstract.Object<DataBaseModel, WeatherToDataBaseModelMapper>() {
//    override fun map(mapper: WeatherToDataBaseModelMapper) =
//        DataBaseModel(city = city, temperature = temperature)
//
//}