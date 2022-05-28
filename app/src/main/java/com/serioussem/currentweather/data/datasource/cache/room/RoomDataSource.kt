package com.serioussem.currentweather.data.datasource.cache.room

import android.database.sqlite.SQLiteException
import com.serioussem.currentweather.R
import com.serioussem.currentweather.data.core.DataResult
import com.serioussem.currentweather.data.core.DataSource
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.data.datasource.models.DataModel
import com.serioussem.currentweather.utils.Constants.FIRST_CITY
import com.serioussem.currentweather.utils.Constants.SECOND_CITY
import javax.inject.Inject

class RoomDataSource @Inject constructor(
    private val dao: WeatherDao,
    private val resourceProvider: ResourceProvider
) : DataSource<String, DataResult<DataModel>> {

    override suspend fun fetchWeather(city: String): DataResult<DataModel> =
        try {
            DataResult.Success(dao.fetchWeather(city = city))
        } catch (e: SQLiteException) {
            DataResult.Error(
                message = e.message.toString()
            )
        } catch (e: Exception) {
            DataResult.Error(
                message = resourceProvider.string(R.string.failed_to_load_data_from_database)
            )
        }

    suspend fun saveWeather(weather: DataModel) =
        dao.saveWeather(weather = weather)

    suspend fun fetchCacheCityList(): MutableList<String> {
        val dataBaseCityList: List<String> = try{
            dao.fetchDataBaseCityList()
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


