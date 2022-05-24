package com.serioussem.currentweather.data.datasource.remote.retrofit

import com.google.gson.Gson
import com.serioussem.currentweather.data.core.InternetConnection
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.data.datasource.mappers.ApiModelToDataMapper
import com.serioussem.currentweather.data.datasource.models.ApiModel
import com.serioussem.currentweather.data.datasource.models.Main
import okhttp3.ResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
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
    private val testResponseSuccess = Response.success(ApiModel(main = Main(temperature = 15.8)))
    private val testResponseError = Response.error(500, ResponseBody.create(null, "error"))


    @Before
    fun beforeMethod() {
    }

    @After
    fun afterMethod() {
    }

    @Test
    suspend fun `test load data if connection is true`() {
        val retrofitDataSource = RetrofitDataSource(
            service = service,
            mapper = mapper,
            resourceProvider = resourceProvider,
            internetConnection = internetConnection
        )
        Mockito.`when`(internetConnection.isConnected()).thenReturn(connectionResultTrue)
        Mockito.`when`(service.fetchWeather(testCity)).thenReturn(testResponseSuccess)

    }
}
