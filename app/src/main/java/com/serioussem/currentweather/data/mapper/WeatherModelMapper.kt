//package com.serioussem.currentweather.data.mapper
//
//import com.serioussem.currentweather.core.Abstract
//import com.serioussem.currentweather.data.model.WeatherModel
//import javax.inject.Inject
//
//interface WeatherModelMapper : Abstract.Mapper {
//
//    fun map(city: String, temperature: Double): WeatherModel
//
//    class Base @Inject constructor() : WeatherModelMapper {
//        override fun map(city: String, temperature: Double) =
//            WeatherModel(city, temperature)
//    }
//}