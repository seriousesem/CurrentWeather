package com.serioussem.currentweather.data.datasource.remote.retrofit


import com.serioussem.currentweather.R
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.data.core.DataResult
import com.serioussem.currentweather.domain.core.DataSource
import com.serioussem.currentweather.data.datasource.mappers.ApiResponseToDataModelMapper
import com.serioussem.currentweather.data.datasource.models.DataWeatherModel
import javax.inject.Inject


class RetrofitDataSource @Inject constructor(
    private val retrofitService: RetrofitService,
    private val apiModelMapper: ApiResponseToDataModelMapper,
    private val resourceProvider: ResourceProvider
): DataSource {

    override suspend fun fetchWeather(city: String): DataResult<DataWeatherModel?> {

        return try {
            val response = retrofitService.fetchWeather(city = city)
            val responseBody = response.body()

            if (response.isSuccessful && responseBody != null) {
                DataResult.Success(
                    DataWeatherModel(
                        city = city,
                        temperature = apiModelMapper.map(responseBody)
                    )
                )
            } else {
                DataResult.Error(message = resourceProvider.string(R.string.city_not_found))
            }
        } catch (e: Exception) {
            DataResult.Error(
                message = resourceProvider.string(R.string.failed_to_load_data)
            )
        }
    }
}
