package com.serioussem.currentweather.data.repository


import android.util.Log
import com.serioussem.currentweather.R
import com.serioussem.currentweather.utils.Constants.FIRST_CITY
import com.serioussem.currentweather.utils.Constants.SECOND_CITY
import com.serioussem.currentweather.data.cache.CacheDataSource
import com.serioussem.currentweather.data.cloud.CloudDataSource
import com.serioussem.currentweather.data.core.NetworkInterceptor
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.domain.model.CityModel
import com.serioussem.currentweather.domain.model.WeatherModel
import com.serioussem.currentweather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val cloudDataSource: CloudDataSource,
    private val cacheDataSource: CacheDataSource,
    private val networkInterceptor: NetworkInterceptor,
    private val resourceProvider: ResourceProvider
) : WeatherRepository {

    private var defaultCityList: MutableList<CityModel> = mutableListOf(
        CityModel(city = FIRST_CITY),
        CityModel(city = SECOND_CITY)
    )
    private var cacheCityList: MutableList<CityModel> = cacheDataSource.fetchCacheCityList()
    private var cityList: MutableList<CityModel> = defaultCityList
    private var cloudResult: ResultState<WeatherModel> = ResultState.Init()
    private var cacheResult: ResultState<WeatherModel> = ResultState.Init()
    private val cloudResultStateList = mutableListOf<ResultState<WeatherModel>>()
    private val cacheResultStateList = mutableListOf<ResultState<WeatherModel>>()

    override fun saveUserCity(cityModel: CityModel) {
        if (cityList.size < 3) {
            cityList.add(CityModel(city = cityModel.city))
        } else cityList[2] = CityModel(city = cityModel.city)
    }

    override suspend fun fetchWeather(): MutableList<ResultState<WeatherModel>> {
        return if (networkInterceptor.isConnected()) {
            fillingCloudResultStateList()
        } else {
            fillingCacheResultStateList()
        }
    }

    private fun fillingCityList() {
        when {
            (cacheCityList.size < 3) ->
                cityList

            (cacheCityList.size >= 2 && cityList.size == 3) -> cityList

            else -> {
                cityList.add(cacheCityList.removeLast())
                cityList
            }
        }
    }

    private fun updateCacheCityList() {
        cacheCityList = cacheDataSource.fetchCacheCityList()
    }

    private suspend fun fillingCloudResultStateList(): MutableList<ResultState<WeatherModel>> {
        updateCacheCityList()
        fillingCityList()
        cityList.forEach { cityModel ->
            cloudResult = cloudDataSource.fetchWeather(cityModel = cityModel)
            if (cloudResult is ResultState.Success) {
                cacheDataSource.saveWeather(cloudResult.data as WeatherModel)
                cloudResultStateList.add(cloudResult)
            } else {
                cityList.remove(cityList.removeLast())
                cloudResultStateList.add(cloudResult)
            }
        }
        return cloudResultStateList
    }

    private suspend fun fillingCacheResultStateList(): MutableList<ResultState<WeatherModel>> {
        cacheResultStateList.add(
            ResultState.Error(
                message = resourceProvider.string(R.string.no_internet_connection_message)
            )
        )
        updateCacheCityList()
        fillingCityList()
        cityList.forEach { cityModel ->
            cacheResult = cacheDataSource.fetchWeather(cityModel = cityModel)
            cacheResultStateList.add(cacheResult)
        }
        return cacheResultStateList
    }

}



