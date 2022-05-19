package com.serioussem.currentweather.data.datasource.remote.retrofit


import com.serioussem.currentweather.R
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.data.datasource.local.room.WeatherEntity
import javax.inject.Inject


class RetrofitDataSource @Inject constructor(
    private val weatherApi: WeatherApi,
    private val resourceProvider: ResourceProvider
) {

    suspend fun fetchWeather(city: String): ResultState<WeatherEntity> {

        return try {
            val response = weatherApi.fetchWeather(city = city)
            val responseBody = response.body()

            if (response.isSuccessful && responseBody != null) {
                ResultState.Success(
                    WeatherEntity(
                        city = city,
                        temperature = responseBody.main.temperature
                    )
                )
            } else {
                ResultState.Error(message = resourceProvider.string(R.string.city_not_found))
            }
        } catch (e: Exception) {
            ResultState.Error(
                message = resourceProvider.string(R.string.failed_to_load_data)
            )
        }
    }
}
