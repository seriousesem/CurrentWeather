package com.serioussem.currentweather.data.repository

import com.serioussem.currentweather.R
import com.serioussem.currentweather.utils.Constants.FIRST_CITY
import com.serioussem.currentweather.utils.Constants.SECOND_CITY
import com.serioussem.currentweather.data.core.DataResult
import com.serioussem.currentweather.data.core.InternetConnection
import com.serioussem.currentweather.data.core.ResourceProvider
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
    private val internetConnection: InternetConnection,
    private val resourceProvider: ResourceProvider
) : WeatherRepository {
    private var cacheCityList: MutableList<String> = mutableListOf()
    private var cityList: MutableList<String> = mutableListOf(FIRST_CITY, SECOND_CITY)
    private var resultList = mutableListOf<DomainResult<DomainModel>>()

    override fun saveUserCity(city: String) {
        if (cityList.size < 3) cityList.add(city) else cityList[2] = city
    }

    override suspend fun fetchWeather(): MutableList<DomainResult<DomainModel>> {
        updateCacheCityList()
        updateCityList()
        return if (internetConnection.isConnected()) fetchRemoteResultList() else fetchCacheResultList()
    }

    private suspend fun fetchRemoteResultList(): MutableList<DomainResult<DomainModel>> {
        cityList.forEach { city ->
            when (val remoteResult = retrofitDataSource.fetchWeather(city = city)) {
                is DataResult.Success<*> -> {
                    roomDataSource.saveWeather(remoteResult.data as DataModel)
                    resultList.add(mapper.map(params = remoteResult))
                }
                is DataResult.Error -> {
                    cityList.remove(cityList.removeLast())
                    resultList.add(mapper.map(params = remoteResult))
                }
                else -> {}
            }
        }
        return resultList
    }

    private suspend fun fetchCacheResultList(): MutableList<DomainResult<DomainModel>> {
        provideInternetConnectionError()
        updateCacheCityList()
        cacheCityList.forEach { city ->
            resultList.add(mapper.map(params = roomDataSource.fetchWeather(city = city)))
        }
        return resultList
    }

    private fun updateCityList() {
        when {
            (cacheCityList.size < 3 || cityList.size == 3) -> cityList
            else -> {
                cityList.add(cacheCityList.removeLast())
            }
        }
    }

    private suspend fun updateCacheCityList() {
        cacheCityList = roomDataSource.fetchCacheCityList()
    }

    private fun provideInternetConnectionError() {
        resultList.add(
            mapper.map(
                DataResult.Error(
                    message = resourceProvider.string(R.string.no_internet_connection_message)
                )
            )
        )
    }
}


