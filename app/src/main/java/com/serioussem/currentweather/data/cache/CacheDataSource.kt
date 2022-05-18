package com.serioussem.currentweather.data.cache


import android.database.sqlite.SQLiteException
import com.serioussem.currentweather.R
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.data.model.CityModel
import com.serioussem.currentweather.data.model.WeatherModel
import com.serioussem.currentweather.utils.Constants.FIRST_CITY
import com.serioussem.currentweather.utils.Constants.SECOND_CITY
import javax.inject.Inject


class CacheDataSource @Inject constructor(
    private val weatherDao: WeatherDao,
    private val resourceProvider: ResourceProvider
) {
    suspend fun fetchWeather(cityModel: CityModel): ResultState<WeatherModel> =
        try {
            ResultState.Success(weatherDao.fetchWeather(city = cityModel.city))
        } catch (e: SQLiteException) {
            ResultState.Error(
                message = e.message.toString()
            )
        } catch (e: Exception) {
            ResultState.Error(
                message = resourceProvider.string(R.string.failed_to_load_data_from_database)
            )
        }

    suspend fun saveWeather(weather: WeatherModel) =
        weatherDao.saveWeather(weather = weather)

    suspend fun fetchCacheCityList(): MutableList<CityModel> =
        try {
            val dataBaseCityList = weatherDao.fetchDataBaseCityList()
            val cacheCityList = mutableListOf<CityModel>()
            dataBaseCityList.forEach { city ->
                cacheCityList.add(CityModel(city = city))
            }
            cacheCityList
        } catch (e: Exception) {
            mutableListOf(CityModel(city = FIRST_CITY), CityModel(city = SECOND_CITY))
        }
}



