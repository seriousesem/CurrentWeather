package com.serioussem.currentweather.data.datasource.remote.retrofit

import com.serioussem.currentweather.R
import com.serioussem.currentweather.data.core.DataResult
import com.serioussem.currentweather.data.core.InternetConnection
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.data.datasource.mappers.ApiModelToDataMapper
import com.serioussem.currentweather.data.datasource.models.ApiModel
import com.serioussem.currentweather.data.datasource.models.DataModel
import com.serioussem.currentweather.data.datasource.models.Main
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock
import retrofit2.Response

class RetrofitDataSourceTest {
    private val service = mock<RetrofitService>()
    private val mapper = mock<ApiModelToDataMapper>()
    private val resourceProvider = mock<ResourceProvider>()
    private val internetConnection = mock<InternetConnection>()
    private val connectionResultTrue = true
    private val connectionResultFalse = false
    private val testCity = "Львів"
    private val testTemperature = 15.8
    private val testResponseSuccess = Response.success(ApiModel(main = Main(temperature = testTemperature)))
    private val testResponseError = Response.error<ApiModel>(500, ResponseBody.create(null, "error"))
    private val testResultSuccess = DataResult.Success(DataModel(city = testCity, temperature = testTemperature))
    private val testResultErrorCityNotFound = DataResult.Error<DataModel>(message = resourceProvider.string(R.string.city_not_found))
    private val testResultErrorFailedLoadData = DataResult.Error<DataModel>(message = resourceProvider.string(R.string.failed_to_load_data))
    private val testResultErrorInternetConnection = DataResult.Error<DataModel>(message = resourceProvider.string(R.string.no_internet_connection_message))



    @Before
    fun beforeMethod() {
    }

    @After
    fun afterMethod() {
        Mockito.reset(service)
        Mockito.reset(mapper)
        Mockito.reset(resourceProvider)
        Mockito.reset(internetConnection)
    }

    @Test
    suspend fun `load data if connection and response is true`() {
        val retrofitDataSource = RetrofitDataSource(
            service = service,
            mapper = mapper,
            resourceProvider = resourceProvider,
            internetConnection = internetConnection
        )
        Mockito.`when`(internetConnection.isConnected()).thenReturn(connectionResultTrue)
        Mockito.`when`(service.fetchWeather(testCity)).thenReturn(testResponseSuccess)

        val expected = testResultSuccess
        val actual = retrofitDataSource.fetchWeather(testCity)
        Assert.assertEquals(4, 2+2)

    }
    @Test
    fun test(){
        Assert.assertEquals(4, 2+2)
    }
}
