package com.serioussem.currentweather.data.cache


import com.serioussem.currentweather.R
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.domain.model.CityModel
import com.serioussem.currentweather.domain.model.WeatherModel
import javax.inject.Inject


class CacheDataSource @Inject constructor(
    private val weatherDao: WeatherDao,
    private val resourceProvider: ResourceProvider
) {

    suspend fun fetchWeather(cityModel: CityModel): ResultState<WeatherModel> =
        try {
            ResultState.Success(weatherDao.fetchWeather(city = cityModel.city))
        } catch (e: Exception) {
            ResultState.Error(
                message = resourceProvider.string(R.string.failed_to_load_data_from_database)
            )
        }

    suspend fun saveWeather(weather: WeatherModel) =
        weatherDao.saveWeather(weather = weather)

    suspend fun updateWeather(weather: WeatherModel) =
        weatherDao.updateWeather(weather = weather)


    fun fetchCacheCityList(): MutableList<CityModel> {
        val dataBaseCityList =  weatherDao.fetchDataBaseCityList()
        val cacheCityList = mutableListOf<CityModel>()
        dataBaseCityList.forEach { city ->
            cacheCityList.add(CityModel(city = city))
        }
        return cacheCityList
    }
}



