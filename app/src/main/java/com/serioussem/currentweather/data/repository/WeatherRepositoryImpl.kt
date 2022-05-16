package com.serioussem.currentweather.data.repository


import com.serioussem.currentweather.R
import com.serioussem.currentweather.core.Constants.FIRST_CITY
import com.serioussem.currentweather.core.Constants.SECOND_CITY
import com.serioussem.currentweather.data.cache.CacheDataSource
import com.serioussem.currentweather.data.cloud.CloudDataSource
import com.serioussem.currentweather.data.core.NetworkInterceptor
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.domain.model.CityModel
import com.serioussem.currentweather.domain.model.WeatherModel
import com.serioussem.currentweather.domain.repository.WeatherRepository
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val cloudDataSource: CloudDataSource,
    private val cacheDataSource: CacheDataSource,
    private val networkInterceptor: NetworkInterceptor,
    private val resourceProvider: ResourceProvider
) : WeatherRepository {


    private val cacheCityList: MutableList<CityModel> = cacheDataSource.fetchCacheCityList()
    private var repositoryCityList: MutableList<CityModel> = mutableListOf(
        CityModel(city = FIRST_CITY),
        CityModel(city = SECOND_CITY)
    )

    private fun selectCityList(): MutableList<CityModel> {
        return when {
            (cacheCityList.size < 3) -> repositoryCityList
            (cacheCityList.size >= 2 && repositoryCityList.size == 3) -> repositoryCityList
            else -> {
                cacheCityList
            }
        }
    }


    override suspend fun fetchWeather(): MutableList<ResultState<WeatherModel>> {
        val cityList = selectCityList()
        var cloudResult: ResultState<WeatherModel>
        var cacheResult: ResultState<WeatherModel> =
        val repositoryResultStateList = mutableListOf<ResultState<WeatherModel>>()
        if (networkInterceptor.isConnected()) {
            cityList.forEach { cityModel ->
                cloudResult = cloudDataSource.fetchWeather(cityModel = cityModel)
                cacheResult = cacheDataSource.fetchWeather(cityModel = cityModel)
                if (cloudResult is ResultState.Success) {
                    repositoryResultStateList.add(cloudResult)
                    if (cacheCityList.contains(CityModel(city = cloudResult.data?.city.toString())))
                        cacheDataSource.updateWeather(cloudResult.data as WeatherModel)
                    else
                        cacheDataSource.saveWeather(cloudResult.data as WeatherModel)
                } else {
                    repositoryResultStateList.add(cloudResult)
                }
            }
            return repositoryResultStateList

        } else {
            repositoryResultStateList.add(
                ResultState.Error(
                    data = cacheResult.data,
                    message = resourceProvider.string(R.string.no_internet_connection_message)
                )
            )
        }
        return repositoryResultStateList
    }


    override fun saveUserCity(cityModel: CityModel) {
        if (repositoryCityList.size < 3) {
            repositoryCityList.add(CityModel(city = cityModel.city))
        } else repositoryCityList[2] = CityModel(city = cityModel.city)
    }


//
//
//    Log.d("Sem", "cacheList: $cacheCityList")
//    Log.d("Sem", "cityList: $defaultCityList")
//    cityList.forEach { city ->
//        launch {
//            val result = fetchWeatherInteractor.fetchWeather(city)
//            if (result is ResultState.Success) {
//                _citiesWeather.value =
//                    ResultState.Success(data = mutableListOf(result.data as WeatherModel))
//
//            } else {
//                _citiesWeather.value = ResultState.Error(
//                    message = result.message.toString()
//                )
//            }
//        }
//    }

}