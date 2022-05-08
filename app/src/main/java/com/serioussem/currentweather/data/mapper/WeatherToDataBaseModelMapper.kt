//package com.serioussem.currentweather.data.mapper
//
//import com.serioussem.currentweather.core.Abstract
//import com.serioussem.currentweather.data.model.DataBaseModel
//import javax.inject.Inject
//
//
//interface WeatherToDataBaseModelMapper : Abstract.Mapper {
//
//    fun map(city: String, temperature: Double): DataBaseModel
//
//    class Base @Inject constructor() : WeatherToDataBaseModelMapper {
//        override fun map(city: String, temperature: Double) =
//            DataBaseModel(city = city, temperature = temperature)
//    }
//}