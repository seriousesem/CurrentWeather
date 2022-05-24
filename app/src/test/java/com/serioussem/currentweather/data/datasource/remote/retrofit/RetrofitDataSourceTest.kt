package com.serioussem.currentweather.data.datasource.remote.retrofit

import com.serioussem.currentweather.R
import com.serioussem.currentweather.data.core.DataResult
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.data.datasource.mappers.ApiModelToDataMapper
import com.serioussem.currentweather.data.datasource.models.DataModel
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.mock


class RetrofitDataSourceTest {
    private val resourceProvider = mock<ResourceProvider>()
    private val internetConnectionTrue = true
    private val internetConnectionFalse = false
    private val city = "Київ"

    private val responseTrue = true
    private val responseFalse = false
    private val responseBody = DataModel(
        city = "Київ",
        temperature = 15.2
    )
    private val cityNotFoundError =  DataResult.Error<DataModel>(message = resourceProvider.string(R.string.city_not_found))
    private val failedLoadDataError = DataResult.Error<DataModel>(message = resourceProvider.string(R.string.failed_to_load_data))
    private val noConnectionError = DataResult.Error<DataModel>(message = resourceProvider.string(R.string.no_internet_connection_message))


    private val expected = DataResult.Success(
        DataModel(
            city = "Київ",
            temperature = 15.2
        )
    )
    private val actual = ""

    @Test
    fun `fetch weather`(): DataResult<DataModel> {
        if (internetConnectionTrue) {
            return try {
                if (responseTrue) {
                    DataResult.Success(responseBody)
                } else {
                    DataResult.Error(message = resourceProvider.string(R.string.city_not_found))
                }
            } catch (e: Exception) {
                DataResult.Error(
                    message = resourceProvider.string(R.string.failed_to_load_data)
                )
            }
        } else {
            return DataResult.Error(
                message = resourceProvider.string(R.string.no_internet_connection_message)
            )
        }

    }
    @Test
    fun `check result if connection and response true`(){
        val expected = responseBody
        val actual = `fetch weather`()
        Assert.assertEquals(expected, actual)
    }
}




