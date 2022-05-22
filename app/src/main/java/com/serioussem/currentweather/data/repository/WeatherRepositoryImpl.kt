package com.serioussem.currentweather.data.repository


import com.serioussem.currentweather.R
import com.serioussem.currentweather.utils.Constants.FIRST_CITY
import com.serioussem.currentweather.utils.Constants.SECOND_CITY
import com.serioussem.currentweather.data.core.DataResult
import com.serioussem.currentweather.data.core.InternetConnection
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.data.datasource.local.room.RoomDataSource
import com.serioussem.currentweather.data.datasource.remote.retrofit.RetrofitDataSource
import com.serioussem.currentweather.data.datasource.mappers.DataResultToDomainResultMapper
import com.serioussem.currentweather.data.datasource.models.DataWeatherModel
import com.serioussem.currentweather.domain.core.DomainResult
import com.serioussem.currentweather.domain.models.DomainModel
import com.serioussem.currentweather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val retrofitDataSource: RetrofitDataSource,
    private val roomDataSource: RoomDataSource,
    private val internetConnection: InternetConnection,
    private val resourceProvider: ResourceProvider,
    private val mapper: DataResultToDomainResultMapper,
) : WeatherRepository {

    private var defaultCityList: MutableList<String> = mutableListOf(
        FIRST_CITY, SECOND_CITY
    )
    private var cacheCityList: MutableList<String> = mutableListOf()
    private var cityList: MutableList<String> = defaultCityList
    private var remoteResult: DataResult<DataWeatherModel?> = DataResult.Init()
    private var localResult: DataResult<DataWeatherModel?> = DataResult.Init()
    private val remoteResultList = mutableListOf<DomainResult<DomainModel?>>()
    private val localResultList = mutableListOf<DomainResult<DomainModel?>>()

    override fun saveUserCity(city: String) {
        if (cityList.size < 3) {
            cityList.add(city)
        } else cityList[2] = city
    }

    override suspend fun fetchWeather(): MutableList<DomainResult<DomainModel?>> {
        return if (internetConnection.isConnected()) {
            updateRemoteResultList()
        } else {
            updateLocalResultList()
        }
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

    private suspend fun updateCacheCityList() {
        cacheCityList = roomDataSource.fetchCacheCityList()
    }

    private fun provideInternetConnectionError() =
        localResultList.add(
            mapper.map(
                data = DataResult.Error(
                    message = resourceProvider.string(R.string.no_internet_connection_message)
                )
            )

        )

    private suspend fun updateRemoteResultList(): MutableList<DomainResult<DomainModel?>> {
//        updateCacheCityList()
        updateCityList()
        cityList.forEach { cityModel ->
            remoteResult = retrofitDataSource.fetchWeather(city = cityModel)
            if (remoteResult is DataResult.Success) {
                roomDataSource.saveWeather(remoteResult.data as DataWeatherModel)
                remoteResultList.add(mapper.map(data = remoteResult))
            } else {
                cityList.remove(cityList.removeLast())
                remoteResultList.add(mapper.map(data = remoteResult))
            }
        }

        return remoteResultList
    }

    private suspend fun updateLocalResultList(): MutableList<DomainResult<DomainModel?>> {
        updateCacheCityList()
        updateCityList()
        provideInternetConnectionError()
        cityList.forEach { cityModel ->
            localResult = roomDataSource.fetchWeather(city = cityModel)
            localResultList.add(mapper.map(data = localResult))
        }
        return localResultList
    }
}



