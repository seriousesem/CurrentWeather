package com.serioussem.currentweather.data.repository


import com.serioussem.currentweather.R
import com.serioussem.currentweather.data.cache.CacheDataSource
import com.serioussem.currentweather.data.cloud.CloudDataSource
import com.serioussem.currentweather.data.core.NetworkInterceptor
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.data.storage.SharedPrefsCityStorage
import com.serioussem.currentweather.domain.core.BaseResult
import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.domain.model.WeatherModel
import com.serioussem.currentweather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val cloudDataSource: CloudDataSource,
    private val cacheDataSource: CacheDataSource,
    private val storage: SharedPrefsCityStorage,
    private val networkInterceptor: NetworkInterceptor,
    private val resourceProvider: ResourceProvider
) : WeatherRepository {

    override suspend fun fetchWeather(city: String): ResultState<WeatherModel> {
        val cacheResult = cacheDataSource.fetchWeather(city = city)
        return if (networkInterceptor.isConnected()) {
            val cloudResult = cloudDataSource.fetchWeather(city = city)

            return if (cloudResult is ResultState.Success) {
                cloudResult.data?.let { cacheDataSource.saveWeather(it) }
                cloudResult

            } else {
                ResultState.Error(data = cacheResult, message = cloudResult.message.toString())
            }
        } else {
            ResultState.Error(data = cacheResult, message =
                resourceProvider.string(R.string.no_internet_connection_message)
            )
        }
    }

    override fun fetchUserCity(): String = storage.fetchUserCity()

    override fun updateUserCity(city: String) = storage.updateUserCity(city = city)

    override suspend fun fetchCityList(): MutableList<String> = cacheDataSource.fetchCityList()

}