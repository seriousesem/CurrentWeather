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
    private val internetConnection: InternetConnection,
    private val resourceProvider: ResourceProvider,
    private val mapper: DataResultToDomainMapper,
) : WeatherRepository {

    private var defaultCityList: MutableList<String> =
        mutableListOf(FIRST_CITY, SECOND_CITY)
    private var cacheCityList: MutableList<String> = mutableListOf()
    private var cityList: MutableList<String> = defaultCityList
    private var remoteResult: DataResult<DataModel> = DataResult.Init()
    private var cacheResult: DataResult<DataModel> = DataResult.Init()
    private val remoteResultList = mutableListOf<DomainResult<DomainModel>>()
    private val cacheResultList = mutableListOf<DomainResult<DomainModel>>()

    override fun saveUserCity(city: String) {
        if (cityList.size < 3) {
            cityList.add(city)
        } else cityList[2] = city
    }

    override suspend fun fetchWeather(): MutableList<DomainResult<DomainModel>> =
        if (internetConnection.isConnected()) updateRemoteResultList() else updateCacheResultList()

    private fun updateCityList() {
        when {
            (cacheCityList.size < 3 || cityList.size == 3) -> cityList

            else -> {
                cityList.add(cacheCityList.removeLast())
                cityList
            }
        }
    }

    private suspend fun updateCacheCityList() {
        cacheCityList = roomDataSource.fetchCacheCityList()
    }

    private fun provideInternetConnectionError() =
        cacheResultList.add(
            mapper.map(
                params = DataResult.Error(
                    message = resourceProvider.string(R.string.no_internet_connection_message)
                )
            )

        )

    private suspend fun updateRemoteResultList(): MutableList<DomainResult<DomainModel>> {
        updateCityList()
        cityList.forEach { cityModel ->
            remoteResult = retrofitDataSource.fetchWeather(city = cityModel)
            if (remoteResult is DataResult.Success) {
                roomDataSource.saveWeather(remoteResult.data as DataModel)
                remoteResultList.add(mapper.map(params = remoteResult))
            } else {
                cityList.remove(cityList.removeLast())
                remoteResultList.add(mapper.map(params = remoteResult))
            }
        }
        updateCacheCityList()
        return remoteResultList
    }

    private suspend fun updateCacheResultList(): MutableList<DomainResult<DomainModel>> {
        updateCacheCityList()
        updateCityList()
        provideInternetConnectionError()
        cityList.forEach { cityModel ->
            cacheResult = roomDataSource.fetchWeather(city = cityModel)
            cacheResultList.add(mapper.map(params = cacheResult))
        }
        return cacheResultList
    }
}



