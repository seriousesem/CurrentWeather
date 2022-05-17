package com.serioussem.currentweather.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serioussem.currentweather.domain.core.ResultState
import com.serioussem.currentweather.domain.interactor.FetchWeatherInteractor
import com.serioussem.currentweather.domain.interactor.SaveUserCityInteractor
import com.serioussem.currentweather.domain.model.CityModel
import com.serioussem.currentweather.domain.model.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val fetchWeatherInteractor: FetchWeatherInteractor,
    private val saveUserCityInteractor: SaveUserCityInteractor
) : ViewModel() {

    private var _citiesWeather :
        MutableLiveData<MutableList<ResultState<WeatherModel>>> = MutableLiveData()

    val citiesWeather: LiveData<MutableList<ResultState<WeatherModel>>> = _citiesWeather

    init {
        fetchWeather()
    }

    fun saveUserCity(city: String) =
        saveUserCityInteractor.saveUserCity(city = CityModel(city = city))

    fun fetchWeather() {
        _citiesWeather.value?.clear()
        viewModelScope.launch {
            _citiesWeather.value =
            fetchWeatherInteractor.fetchWeather()
        }
    }
}
