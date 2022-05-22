package com.serioussem.currentweather.data.datasource.local.room


import android.database.sqlite.SQLiteException
import com.serioussem.currentweather.R
import com.serioussem.currentweather.data.core.DataResult
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.domain.core.DataSource
import com.serioussem.currentweather.data.datasource.models.DataWeatherModel
import com.serioussem.currentweather.utils.Constants.FIRST_CITY
import com.serioussem.currentweather.utils.Constants.SECOND_CITY
import javax.inject.Inject


class RoomDataSource @Inject constructor(
    private val weatherDao: WeatherDao,
    private val resourceProvider: ResourceProvider
) : DataSource {

    override suspend fun fetchWeather(city: String): DataResult<DataWeatherModel?> =
        try {
            DataResult.Success(weatherDao.fetchWeather(city = city))
        } catch (e: SQLiteException) {
            DataResult.Error(
                message = e.message.toString()
            )
        } catch (e: Exception) {
            DataResult.Error(
                message = resourceProvider.string(R.string.failed_to_load_data_from_database)
            )
        }

    suspend fun saveWeather(weather: DataWeatherModel) =
        weatherDao.saveWeather(weather = weather)

    suspend fun fetchCacheCityList(): MutableList<String> {
        val dataBaseCityList: List<String> = try{
            weatherDao.fetchDataBaseCityList()
        }catch (e: Exception){
            listOf(FIRST_CITY, SECOND_CITY)
        }
        val cacheCityList = mutableListOf<String>()
            dataBaseCityList.forEach { city ->
                cacheCityList.add(city)
            }
        return cacheCityList
    }
}


