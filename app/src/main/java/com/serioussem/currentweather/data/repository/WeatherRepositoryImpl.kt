package com.serioussem.currentweather.data.repository


import com.serioussem.currentweather.R
import com.serioussem.currentweather.utils.Constants.FIRST_CITY
import com.serioussem.currentweather.utils.Constants.SECOND_CITY
import com.serioussem.currentweather.data.cache.CacheDataSource
import com.serioussem.currentweather.data.cloud.CloudDataSource
import com.serioussem.currentweather.data.core.InternetConnection
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.data.model.CityModel
import com.serioussem.currentweather.data.model.WeatherModel
import com.serioussem.currentweather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val cloudDataSource: CloudDataSource,
    private val cacheDataSource: CacheDataSource,
    private val internetConnection: InternetConnection,
    private val resourceProvider: ResourceProvider
) : WeatherRepository {

    private var defaultCityList: MutableList<CityModel> = mutableListOf(
        CityModel(city = FIRST_CITY),
        CityModel(city = SECOND_CITY)
    )
    private var cacheCityList: MutableList<CityModel> = mutableListOf()
    private var cityList: MutableList<CityModel> = defaultCityList
    private var cloudResult: ResultState<WeatherModel> = ResultState.Init()
    private var cacheResult: ResultState<WeatherModel> = ResultState.Init()
    private val cloudResultList = mutableListOf<ResultState<WeatherModel>>()
    private val cacheResultList = mutableListOf<ResultState<WeatherModel>>()

    override fun saveUserCity(cityModel: CityModel) {
        if (cityList.size < 3) {
            cityList.add(CityModel(city = cityModel.city))
        } else cityList[2] = CityModel(city = cityModel.city)
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
        cacheCityList = cacheDataSource.fetchCacheCityList()
    }

    private fun provideInternetConnectionError() =
        cacheResultList.add(
            ResultState.Error(
                message = resourceProvider.string(R.string.no_internet_connection_message)
            )
        )

    private suspend fun updateCloudResultList(): MutableList<ResultState<WeatherModel>> {
        updateCacheCityList()
        updateCityList()
        cityList.forEach { cityModel ->
            cloudResult = cloudDataSource.fetchWeather(cityModel = cityModel)
            if (cloudResult is ResultState.Success) {
                cacheDataSource.saveWeather(cloudResult.data as WeatherModel)
                cloudResultList.add(cloudResult)
            } else {
                cityList.remove(cityList.removeLast())
                cloudResultList.add(cloudResult)
            }
        }
        return cloudResultList
    }

    private suspend fun updateCacheResultList(): MutableList<ResultState<WeatherModel>> {
        updateCacheCityList()
        updateCityList()
        provideInternetConnectionError()
        cityList.forEach { cityModel ->
            cacheResult = cacheDataSource.fetchWeather(cityModel = cityModel)
            cacheResultList.add(cacheResult)
        }
        return cacheResultList
    }

}



