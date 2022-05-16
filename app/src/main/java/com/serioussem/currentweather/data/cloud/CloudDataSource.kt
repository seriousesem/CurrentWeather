package com.serioussem.currentweather.data.cloud


import com.serioussem.currentweather.R
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.domain.model.CityModel
import com.serioussem.currentweather.domain.model.WeatherModel
import javax.inject.Inject


class CloudDataSource @Inject constructor(
    private val weatherApi: WeatherApi,
    private val resourceProvider: ResourceProvider
) {

    suspend fun fetchWeather(cityModel: CityModel): ResultState<WeatherModel> {

        return try {
            val response = weatherApi.fetchWeather(city = cityModel.city)
            val responseBody = response.body()

            if (response.isSuccessful && responseBody != null) {
                ResultState.Success(
                    WeatherModel(
                        city = cityModel.city,
                        temperature = responseBody.main.temperature
                    )
                )
            } else {
                ResultState.Error(message = resourceProvider.string(R.string.city_not_found))
            }
        } catch (e: Exception) {
            ResultState.Error(
                message = resourceProvider.string(R.string.failed_to_load_data))
        }
    }
}
