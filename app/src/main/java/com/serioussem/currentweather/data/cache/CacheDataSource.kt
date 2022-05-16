package com.serioussem.currentweather.data.cache


import com.serioussem.currentweather.R
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.domain.model.WeatherModel
import javax.inject.Inject


class CacheDataSource @Inject constructor(private val weatherDao: WeatherDao, private val resourceProvider: ResourceProvider) {

    suspend fun fetchWeather(city: String): ResultState<WeatherModel> =
        try {
            ResultState.Success(weatherDao.fetchWeather(city = city))
        }catch (e: Exception) {
            ResultState.Error(
                message = resourceProvider.string(R.string.failed_to_load_data_from_database)
            )
        }


    suspend fun saveWeather(weather: WeatherModel) =
        weatherDao.saveWeather(weather = weather)

    suspend fun updateWeather(weather: WeatherModel) =
        weatherDao.updateWeather(weather = weather)


    fun fetchCityList(): List<String> =
        weatherDao.fetchCityList()

}



