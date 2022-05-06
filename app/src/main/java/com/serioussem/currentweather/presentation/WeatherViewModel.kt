package com.serioussem.currentweather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.serioussem.currentweather.core.Constants.FIRST_CITY
import com.serioussem.currentweather.core.Constants.SECOND_CITY
import com.serioussem.currentweather.domain.core.BaseResult
import com.serioussem.currentweather.domain.interactor.FetchWeatherInteractor
import com.serioussem.currentweather.domain.model.WeatherModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val fetchWeatherInteractor: FetchWeatherInteractor
) : ViewModel() {

    private val _state = MutableStateFlow<WeatherActivityState>(WeatherActivityState.Init)
    val state: StateFlow<WeatherActivityState> = _state

    private val _firstCityWeather = MutableStateFlow(WeatherModel())
    val firstCityWeather: Flow<WeatherModel> = _firstCityWeather
    private val _secondCityWeather = MutableStateFlow(WeatherModel())
    val secondCityWeather: Flow<WeatherModel> = _secondCityWeather

    init {
        fetchWeather()
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

    fun fetchWeather() {

        val firstCity = FIRST_CITY
        val secondCity = SECOND_CITY

        viewModelScope.launch {
            launch {
                fetchWeatherInteractor.fetchWeather(firstCity)
                    .onStart { showLoading() }
                    .catch { e ->
                        hideLoading()
                        showSnackbar(e.message.toString())
                    }
                    .collect { result ->
                        hideLoading()
                        when (result) {
                            is BaseResult.Success<*> -> {
                                _secondCityWeather.value = result.data as WeatherModel
                            }
                            is BaseResult.Error -> {
                                if (result.error.code != 0) {
                                    showSnackbar(result.error.message)
                                }
                            }
                        }
                    }
            }
            launch {
                fetchWeatherInteractor.fetchWeather(secondCity)
                    .onStart { showLoading() }
                    .catch { e ->
                        hideLoading()
                        showSnackbar(e.message.toString())
                    }
                    .collect { result ->
                        hideLoading()
                        when (result) {
                            is BaseResult.Success<*> -> {
                                _secondCityWeather.value = result.data as WeatherModel
                            }
                            is BaseResult.Error -> {
                                if (result.error.code != 0) {
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