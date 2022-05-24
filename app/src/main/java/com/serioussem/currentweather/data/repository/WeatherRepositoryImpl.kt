package com.serioussem.currentweather.data.repository

import android.util.Log
import com.serioussem.currentweather.utils.Constants.FIRST_CITY
import com.serioussem.currentweather.utils.Constants.SECOND_CITY
import com.serioussem.currentweather.data.core.DataResult
import com.serioussem.currentweather.data.datasource.cache.room.RoomDataSource
import com.serioussem.currentweather.data.datasource.remote.retrofit.RetrofitDataSource
import com.serioussem.currentweather.data.datasource.mappers.DataResultToDomainMapper
import com.serioussem.currentweather.data.datasource.models.DataModel
import com.serioussem.currentweather.domain.core.DomainResult
import com.serioussem.currentweather.domain.models.DomainModel
import com.serioussem.currentweather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val retrofitDataSource: RetrofitDataSource,
    private val roomDataSource: RoomDataSource,
    private val mapper: DataResultToDomainMapper,
) : WeatherRepository {

    private var defaultCityList: MutableList<String> =
        mutableListOf(FIRST_CITY, SECOND_CITY)
    private var cacheCityList: MutableList<String> = mutableListOf()
    private var cityList: MutableList<String> = defaultCityList
    private val resultList = mutableListOf<DomainResult<DomainModel>>()

    override fun saveUserCity(city: String) {
        if (cityList.size < 3) {
            cityList.add(city)
        } else cityList[2] = city
    }
    private fun updateCityList() {
        when {
            (cacheCityList.size < 3 || cityList.size == 3) -> cityList
            else -> {
                cityList.add(cacheCityList.removeLast())
                cityList
            }
        }
    }

    override suspend fun fetchWeather(): MutableList<DomainResult<DomainModel>> =
        updateResultList()

    private suspend fun updateResultList(): MutableList<DomainResult<DomainModel>>{
        updateCacheCityList()
        updateCityList()
        Log.d("Sem", "cityList $cityList")
        Log.d("Sem", "cacheCityList $cacheCityList")
        cityList.forEach { city ->
            when (val remoteResult = retrofitDataSource.fetchWeather(city = city)) {
                is DataResult.Success -> {
                    roomDataSource.saveWeather(remoteResult.data as DataModel)
                    resultList.add(mapper.map(params = remoteResult))
                }
                else -> {
                    cityList.remove(cityList.removeLast())
                    resultList.add(mapper.map(params = remoteResult))
                    resultList.add(mapper.map(params = roomDataSource.fetchWeather(city = city)))
                }
            }
        }
        return resultList
    }

    private suspend fun updateCacheCityList() {
        cacheCityList = roomDataSource.fetchCacheCityList()
    }
}



