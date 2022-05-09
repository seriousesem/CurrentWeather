package com.serioussem.currentweather.domain.repository



import com.serioussem.currentweather.domain.core.BaseResult
import com.serioussem.currentweather.domain.core.Failure
import com.serioussem.currentweather.domain.model.WeatherModel
import  kotlinx.coroutines.flow.Flow

interface WeatherRepository{

    suspend fun fetchWeather(city: String): Flow<BaseResult<WeatherModel, Failure>>

    fun fetchUserCity(): String

    fun updateUserCity(city: String)

    suspend fun fetchCityList(): MutableList<String>

}