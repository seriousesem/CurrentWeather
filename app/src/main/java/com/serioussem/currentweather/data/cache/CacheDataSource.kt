package com.serioussem.currentweather.data.cache


import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.domain.model.CityModel
import com.serioussem.currentweather.domain.model.WeatherModel
import javax.inject.Inject


class CacheDataSource @Inject constructor(
    private val weatherDao: WeatherDao
) {

    suspend fun fetchWeather(cityModel: CityModel): ResultState<WeatherModel> =
        ResultState.Success(weatherDao.fetchWeather(city = cityModel.city))

    suspend fun saveWeather(weather: WeatherModel) =
        weatherDao.saveWeather(weather = weather)

    fun fetchCacheCityList(): MutableList<CityModel> {
        val dataBaseCityList = weatherDao.fetchDataBaseCityList()
        val cacheCityList = mutableListOf<CityModel>()
        dataBaseCityList.forEach { city ->
            cacheCityList.add(CityModel(city = city))
        }
        return cacheCityList
    }
}



