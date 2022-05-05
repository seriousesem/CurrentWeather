package com.serioussem.currentweather.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
): ViewModel() {

    private val _state = MutableStateFlow<WeatherActivityState>(WeatherActivityState.Init)
    val state : StateFlow<WeatherActivityState> = _state

    private val _weather = MutableStateFlow(WeatherModel())
    val weather: Flow<WeatherModel> = _weather

    private fun showLoading(){
        _state.value = WeatherActivityState.IsLoading(true)
    }

    private fun hideLoading(){
        _state.value = WeatherActivityState.IsLoading(false)
    }

    private fun showSnackbar(message: String){
        _state.value = WeatherActivityState.ShowSnackbar(message)
    }

    fun fetchWeather(city: String ){
        viewModelScope.launch {
            fetchWeatherInteractor.execute(city)
                .onStart { showLoading() }
                .catch { e ->
                    hideLoading()
                    showSnackbar(e.message.toString())
                }
                .collect { result ->
                    hideLoading()
                    when(result){
                        is BaseResult.Success<*> -> {
                            _weather.value = result.data as WeatherModel
                        }
                        is BaseResult.Error -> {
                            if (result.error.code != 0){
                                showSnackbar(result.error.message)
                            }
                        }
                    }
                }
        }
    }

    sealed class WeatherActivityState{

        object Init : WeatherActivityState()
        data class IsLoading(private val isLoading: Boolean): WeatherActivityState()
        data class ShowSnackbar(private val message: String): WeatherActivityState()
    }
}