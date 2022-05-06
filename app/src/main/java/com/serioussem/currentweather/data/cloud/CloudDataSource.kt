package com.serioussem.currentweather.data.cloud


import com.serioussem.currentweather.core.Constants.FIRST_CITY
import com.serioussem.currentweather.core.Constants.SECOND_CITY
import com.serioussem.currentweather.data.core.exception.NoInternetException
import com.serioussem.currentweather.domain.core.BaseResult
import com.serioussem.currentweather.domain.core.Failure
import com.serioussem.currentweather.domain.model.WeatherModel
import javax.inject.Inject


class CloudDataSource @Inject constructor(
    private val weatherApi: WeatherApi
    ){
    var cityList: MutableList<String> = mutableListOf(FIRST_CITY, SECOND_CITY)

        suspend fun fetchWeather(city: String): BaseResult<WeatherModel, Failure> {
            cityList.forEach {
                return try {
                    val response = weatherApi.fetchWeather(city = city)
                    val responseBody = response.body()
                    if (response.isSuccessful && responseBody != null) {
                        val weather =
                            WeatherModel(city = city, temperature = responseBody.main.temperature)
                        BaseResult.Success(weather)
                    } else {
                        BaseResult.Error(Failure(response.code(), response.message()))
                    }

                } catch (e: NoInternetException) {
                    BaseResult.Error(Failure(0, e.message))
                } catch (e: Exception) {
                    BaseResult.Error(Failure(-1, e.message.toString()))
                }
            }
        }
    }
}
