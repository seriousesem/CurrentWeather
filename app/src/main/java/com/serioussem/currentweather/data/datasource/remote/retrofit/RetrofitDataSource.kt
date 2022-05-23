package com.serioussem.currentweather.data.datasource.remote.retrofit


import com.serioussem.currentweather.R
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.data.core.DataResult
import com.serioussem.currentweather.data.core.InternetConnection
import com.serioussem.currentweather.domain.core.DataSource
import com.serioussem.currentweather.data.datasource.mappers.ApiModelToDataMapper
import com.serioussem.currentweather.data.datasource.models.DataModel
import javax.inject.Inject


class RetrofitDataSource @Inject constructor(
    private val service: RetrofitService,
    private val mapper: ApiModelToDataMapper,
    private val resourceProvider: ResourceProvider,
    private val internetConnection: InternetConnection
) : DataSource<String, DataResult<DataModel>> {

    override suspend fun fetchWeather(city: String): DataResult<DataModel> {
        if (internetConnection.isConnected()) {
            return try {
                val response = service.fetchWeather(city = city)
                val responseBody = response.body()
                if (response.isSuccessful && responseBody != null) {
                    DataResult.Success(
                        DataModel(
                            city = city,
                            temperature = mapper.map(responseBody)
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
        else {
            return DataResult.Error(
                message = resourceProvider.string(R.string.no_internet_connection_message)
            )
        }
    }
}
