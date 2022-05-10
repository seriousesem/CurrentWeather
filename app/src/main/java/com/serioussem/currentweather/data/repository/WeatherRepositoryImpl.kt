package com.serioussem.currentweather.data.repository


import com.serioussem.currentweather.R
import com.serioussem.currentweather.data.cache.CacheDataSource
import com.serioussem.currentweather.data.cloud.CloudDataSource
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.data.storage.SharedPrefsCityStorage
import com.serioussem.currentweather.domain.core.BaseResult
import com.serioussem.currentweather.domain.core.Failure
import com.serioussem.currentweather.domain.model.WeatherModel
import com.serioussem.currentweather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val cloudDataSource: CloudDataSource,
    private val cacheDataSource: CacheDataSource,
    private val storage: SharedPrefsCityStorage,
    private val resourceProvider: ResourceProvider
) : WeatherRepository {

    override suspend fun fetchWeather(city: String): BaseResult<WeatherModel, Failure> {

        val cloudResult = cloudDataSource.fetchWeather(city = city)
        val cacheResult = cacheDataSource.fetchWeather(city = city)

        return if (cloudResult is BaseResult.Success) {
            cacheDataSource.saveWeather(cloudResult.data)
            cloudResult

        } else {
            BaseResult.Error(Failure(3, resourceProvider.string(R.string.failed_to_load_data)))
            BaseResult.Success(cacheResult)
        }
    }

    override fun fetchUserCity(): String = storage.fetchUserCity()

    override fun updateUserCity(city: String) = storage.updateUserCity(city = city)

    override suspend fun fetchCityList(): MutableList<String> = cacheDataSource.fetchCityList()

}