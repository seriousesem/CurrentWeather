package com.serioussem.currentweather.data.cloud



import com.serioussem.currentweather.R
import com.serioussem.currentweather.data.core.NetworkInterceptor
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.domain.core.BaseResult
import com.serioussem.currentweather.domain.core.Failure
import com.serioussem.currentweather.domain.model.WeatherModel
import javax.inject.Inject


class CloudDataSource @Inject constructor(
    private val weatherApi: WeatherApi,
    private val networkInterceptor: NetworkInterceptor,
    private val resourceProvider: ResourceProvider
) {

    suspend fun fetchWeather(city: String): BaseResult<WeatherModel, Failure> {
        return if (networkInterceptor.isConnected()) {
            try {
                val response = weatherApi.fetchWeather(city = city)
                val responseBody = response.body()

                if (response.isSuccessful && responseBody != null) {
                    val weather =
                        WeatherModel(city = city, temperature = responseBody.main.temperature)
                    BaseResult.Success(weather)
                } else {
                    BaseResult.Error(Failure(1, resourceProvider.string(R.string.city_not_found)))
                }
            } catch (e: Exception) {
                BaseResult.Error(Failure(2, e.message.toString()))

            }
        } else {
            BaseResult.Error(
                Failure(
                    0,
                    resourceProvider.string(R.string.no_internet_connection_message)
                )
            )
        }
    }
}
