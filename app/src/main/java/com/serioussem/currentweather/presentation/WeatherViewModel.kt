package com.serioussem.currentweather.presentation


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serioussem.currentweather.core.Constants.FIRST_CITY
import com.serioussem.currentweather.core.Constants.SECOND_CITY
import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.domain.interactor.FetchCityListInteractor
import com.serioussem.currentweather.domain.interactor.FetchUserCityInteractor
import com.serioussem.currentweather.domain.interactor.FetchWeatherInteractor
import com.serioussem.currentweather.domain.model.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val fetchWeatherInteractor: FetchWeatherInteractor,
    private val fetchCityListInteractor: FetchCityListInteractor
) : ViewModel() {

    private val cacheCityList : List<String> = fetchCityListInteractor.fetchCityList()
    private var defaultCityList: MutableList<String> = mutableListOf(FIRST_CITY, SECOND_CITY)


    private var _citiesWeather =
        MutableLiveData<ResultState<MutableList<WeatherModel>>>(ResultState.Init())
    val citiesWeather: LiveData<ResultState<MutableList<WeatherModel>>> = _citiesWeather


    init {
        fetchCitiesWeather()
    }

    fun editUserCity(city: String) {

        if (defaultCityList.size <= 2) defaultCityList.add(city) else defaultCityList[2] = city
    }

    private fun selectCityList(): List<String> {

        return when {
            (cacheCityList.size < 3) -> defaultCityList
            (cacheCityList.size >= 2 && defaultCityList.size == 3) -> defaultCityList
            else -> {
                cacheCityList
            }
        }
    }

    fun fetchCitiesWeather() {
        _citiesWeather.value = ResultState.Loading()
        val cityList = selectCityList()
        Log.d("Sem", "cacheList: $cacheCityList")
        Log.d("Sem", "cityList: $defaultCityList")
        viewModelScope.launch(Dispatchers.IO) {
            cityList.forEach { city ->
                launch{
                    val result = fetchWeatherInteractor.fetchWeather(city)
                    if (result is ResultState.Success) {
                        _citiesWeather.value =
                            ResultState.Success(data = mutableListOf(result.data as WeatherModel))

                    } else {
                        _citiesWeather.value = ResultState.Error(
                            data = mutableListOf(result.data as WeatherModel),
                            message = result.message.toString()
                        )
                        defaultCityList.removeAt(2)
                    }
                }
            }
        }
    }
}
