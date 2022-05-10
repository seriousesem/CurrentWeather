package com.serioussem.currentweather.presentation


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serioussem.currentweather.core.Constants.FIRST_CITY
import com.serioussem.currentweather.core.Constants.SECOND_CITY
import com.serioussem.currentweather.domain.core.BaseResult
import com.serioussem.currentweather.domain.interactor.FetchCityListInteractor
import com.serioussem.currentweather.domain.interactor.FetchUserCityInteractor
import com.serioussem.currentweather.domain.interactor.FetchWeatherInteractor
import com.serioussem.currentweather.domain.interactor.UpdateUserCityInteractor
import com.serioussem.currentweather.domain.model.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val fetchWeatherInteractor: FetchWeatherInteractor,
    private val updateUserCityInteractor: UpdateUserCityInteractor,
    private val fetchUserCityInteractor: FetchUserCityInteractor,
    private val fetchCityListInteractor: FetchCityListInteractor
) : ViewModel() {

    private val userCity = fetchUserCityInteractor.fetchUserCity()
    private var cityList: MutableList<String> = mutableListOf(FIRST_CITY, SECOND_CITY)
    private var cacheList: MutableList<String> = mutableListOf()


    private var _citiesWeather = MutableLiveData<WeatherActivityState>(WeatherActivityState.Init)
    val citiesWeather: LiveData<WeatherActivityState> = _citiesWeather


    init {
        fetchCitiesWeather()
    }

    fun editUserCity(city: String) {

        if (cityList.size <= 3) cityList.add(city) else cityList[2] = city
    }

    private fun updateUserCity(city: String) =
        updateUserCityInteractor.updateUserCity(city = city)

    private fun updateSityList(city: String) {
        if (cityList.size <= 2) cityList.add(city) else cityList[2] = city
    }


    private suspend fun updateCityList() {
        cacheList.clear()
        cacheList.addAll(fetchCityListInteractor.fetchCityList())
        cityList.add(userCity)
        Log.d("Sem", "cacheList: $cacheList")
    }

    fun fetchCitiesWeather() {
        _citiesWeather.value = WeatherActivityState.Loading
        viewModelScope.launch {
            updateCityList()
            cityList.forEach { city ->
                launch {
                    when (val result = fetchWeatherInteractor.fetchWeather(city)) {
                        is BaseResult.Success<*> -> {
                            _citiesWeather.value =
                                WeatherActivityState.Success(result.data)
                            when (city) {
                                FIRST_CITY -> {}
                                SECOND_CITY -> {}
                                city -> {
                                    updateUserCity(city = city)
                                    updateSityList(city = city)
                                }
                            }
                        }
                        is BaseResult.Error -> {
                            _citiesWeather.value = WeatherActivityState.Error(result.error.message)
                        }
                    }
                }
            }
        }

    }

    sealed class WeatherActivityState {
        object Init : WeatherActivityState()
        object Loading : WeatherActivityState()
        data class Success<T>(val data: T): WeatherActivityState()
        data class Error(val message: String) : WeatherActivityState()
    }

}


