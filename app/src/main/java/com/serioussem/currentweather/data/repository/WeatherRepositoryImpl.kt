package com.serioussem.currentweather.data.repository


import com.serioussem.currentweather.R
import com.serioussem.currentweather.utils.Constants.FIRST_CITY
import com.serioussem.currentweather.utils.Constants.SECOND_CITY
import com.serioussem.currentweather.data.datasource.local.room.RoomDataSource
import com.serioussem.currentweather.data.datasource.remote.retrofit.RetrofitDataSource
import com.serioussem.currentweather.data.core.InternetConnection
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.data.datasource.local.room.WeatherEntity
import com.serioussem.currentweather.domain.models.WeatherModel
import com.serioussem.currentweather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val retrofitDataSource: RetrofitDataSource,
    private val roomDataSource: RoomDataSource,
    private val internetConnection: InternetConnection,
    private val resourceProvider: ResourceProvider
) : WeatherRepository {

    private var defaultCityList: MutableList<String> = mutableListOf(
        FIRST_CITY, SECOND_CITY
    )
    private var cacheCityList: MutableList<String> = mutableListOf()
    private var cityList: MutableList<String> = defaultCityList
    private var cloudResult: ResultState<WeatherEntity> = ResultState.Init()
    private var cacheResult: ResultState<WeatherEntity> = ResultState.Init()
    private val cloudResultList = mutableListOf<ResultState<WeatherEntity>>()
    private val cacheResultList = mutableListOf<ResultState<WeatherEntity>>()

    override fun saveUserCity(city: String) {
        if (cityList.size < 3) {
            cityList.add(city)
        } else cityList[2] = city
    }

    override suspend fun fetchWeather(): MutableList<ResultState<WeatherModel>> {
        return if (internetConnection.isConnected()) {
            updateCloudResultList()
        } else {
            updateCacheResultList()
        }
    }

    private fun updateCityList() {
        when {
            (cacheCityList.size >= 2 && cityList.size == 3) -> cityList

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
            ResultState.Error(
                message = resourceProvider.string(R.string.no_internet_connection_message)
            )
        )

    private suspend fun updateCloudResultList(): MutableList<ResultState<WeatherEntity>> {
        updateCacheCityList()
        updateCityList()
        cityList.forEach { cityModel ->
            cloudResult = retrofitDataSource.fetchWeather(city = cityModel)
            if (cloudResult is ResultState.Success) {
                roomDataSource.saveWeather(cloudResult.data as WeatherEntity)
                cloudResultList.add(cloudResult)
            } else {
                cityList.remove(cityList.removeLast())
                cloudResultList.add(cloudResult)
            }
        }
        return cloudResultList
    }

    private suspend fun updateCacheResultList(): MutableList<ResultState<WeatherEntity>> {
        updateCacheCityList()
        updateCityList()
        provideInternetConnectionError()
        cityList.forEach { cityModel ->
            cacheResult = roomDataSource.fetchWeather(city = cityModel)
            cacheResultList.add(cacheResult)
        }
        return cacheResultList
    }

}



