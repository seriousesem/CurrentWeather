package com.serioussem.currentweather.data.repository


import com.serioussem.currentweather.data.cloud.CloudDataSource
import com.serioussem.currentweather.domain.core.BaseResult
import com.serioussem.currentweather.domain.core.Failure
import com.serioussem.currentweather.domain.model.WeatherModel
import com.serioussem.currentweather.domain.repository.WeatherRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val cloudDataSource: CloudDataSource
): WeatherRepository {

    override suspend fun fetchWeather(city: String): Flow<BaseResult<WeatherModel, Failure>> {
        return flow {
            val result = cloudDataSource.fetchWeather(city = city)
            emit(result)
        }
    }
}