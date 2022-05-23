package com.serioussem.currentweather.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serioussem.currentweather.domain.core.DomainResult
import com.serioussem.currentweather.domain.usecase.FetchWeatherUseCase
import com.serioussem.currentweather.domain.usecase.SaveUserCityUseCase
import com.serioussem.currentweather.domain.models.DomainModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.serioussem.currentweather.utils.Dispatchers
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val fetchWeatherUseCase: FetchWeatherUseCase,
    private val saveUserCityUseCase: SaveUserCityUseCase,
    private val dispatchers: Dispatchers
) : ViewModel() {

    private var weatherMutableList:
            MutableLiveData<MutableList<DomainResult<DomainModel>>> = MutableLiveData()

    val weatherList: LiveData<MutableList<DomainResult<DomainModel>>> = weatherMutableList

    init {
        fetchWeather()
    }

    fun saveUserCity(city: String) =
        saveUserCityUseCase.saveUserCity(city = city)

    fun fetchWeather() {
        dispatchers.launchBackground(viewModelScope) {
            weatherMutableList.value =
                fetchWeatherUseCase.fetchWeather()
        }
    }
}
