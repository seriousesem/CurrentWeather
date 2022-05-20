package com.serioussem.currentweather.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serioussem.currentweather.domain.core.DomainResult
import com.serioussem.currentweather.domain.usecase.FetchWeatherUseCase
import com.serioussem.currentweather.domain.usecase.SaveUserCityUseCase
import com.serioussem.currentweather.domain.models.DomainWeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val fetchWeatherUseCase: FetchWeatherUseCase,
    private val saveUserCityUseCase: SaveUserCityUseCase
) : ViewModel() {

    private var _citiesWeather :
        MutableLiveData<MutableList<DomainResult<DomainWeatherModel?>>> = MutableLiveData()

    val citiesWeather: LiveData<MutableList<DomainResult<DomainWeatherModel?>>> = _citiesWeather

    init {
        fetchWeather()
    }

    fun saveUserCity(city: String) =
        saveUserCityUseCase.saveUserCity(city = city)

    fun fetchWeather() {
        viewModelScope.launch(Dispatchers.IO) {
            _citiesWeather.value =
            fetchWeatherUseCase.fetchWeather()
        }
    }
}
