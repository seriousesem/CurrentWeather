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

    private val _state = MutableLiveData<WeatherActivityState>(WeatherActivityState.Init)
    val state: LiveData<WeatherActivityState> = _state

    private var _defaultCitiesWeather = MutableLiveData<MutableList<WeatherModel>>(mutableListOf())
    val defaultCitiesWeather: LiveData<MutableList<WeatherModel>> = _defaultCitiesWeather


    init {
        fetchDefaultCitiesWeather()

    }

    fun editUserCity(city: String) {

        if (cityList.size <= 3) cityList.add(city) else cityList[2] = city
    }

    private fun updateUserCity(city: String) =
        updateUserCityInteractor.updateUserCity(city = city)

    private fun updateSityList(city: String) {
        if (cityList.size <= 2) cityList.add(city) else cityList[2] = city
    }

    private fun showLoading() {
        _state.value = WeatherActivityState.Loading(true)
    }

    private fun hideLoading() {
        _state.value = WeatherActivityState.Loading(false)
    }

    private fun showSnackbar(message: String) {
        _state.value = WeatherActivityState.ShowSnackbar(message)
    }

    private suspend fun updateCityList() {
        val citySet: MutableSet<String> = mutableSetOf()
        citySet.addAll(fetchCityListInteractor.fetchCityList())
        cacheList = citySet.toMutableList()
        Log.d("Sem", "cacheList: $cacheList")
    }
    fun fetchDefaultCitiesWeather() {
        viewModelScope.launch {
            updateCityList()
            cityList.forEach { city ->
                launch {
                    fetchWeatherInteractor.fetchWeather(city)
                        .onStart { showLoading() }
                        .catch { e ->
                            hideLoading()
                            showSnackbar(e.message.toString())
                        }
                        .collect { result ->
                            hideLoading()
                            when (result) {
                                is BaseResult.Success<*> -> {
                                    _defaultCitiesWeather.value =
                                        mutableListOf(result.data as WeatherModel)
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
                                    showSnackbar(result.error.message)
                                }
                            }
                        }
                }
            }
        }
    }

    sealed class WeatherActivityState {
        object Init : WeatherActivityState()
        data class Loading(val isLoading: Boolean) : WeatherActivityState()
        data class ShowSnackbar(val message: String) : WeatherActivityState()
    }
}