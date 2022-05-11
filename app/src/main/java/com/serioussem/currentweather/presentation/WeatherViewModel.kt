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
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val fetchWeatherInteractor: FetchWeatherInteractor,
//    private val updateUserCityInteractor: UpdateUserCityInteractor,
    private val fetchUserCityInteractor: FetchUserCityInteractor,
    private val fetchCityListInteractor: FetchCityListInteractor
) : ViewModel() {

    private val userCity = fetchUserCityInteractor.fetchUserCity()
    private var cityList: MutableList<String> = mutableListOf(FIRST_CITY, SECOND_CITY, userCity)



    private var _citiesWeather =
        MutableLiveData<ResultState<MutableList<WeatherModel>>>(ResultState.Init())
    val citiesWeather: LiveData<ResultState<MutableList<WeatherModel>>> = _citiesWeather


    init {
//        updateCityList()
        fetchCitiesWeather()
    }

    fun editUserCity(city: String) {

        if (cityList.size <= 2) cityList.add(city) else cityList[2] = city
    }

//    private fun updateUserCity(city: String) =
//        updateUserCityInteractor.updateUserCity(city = city)

//    private fun updateCityList() {
//        cacheList.clear()
//        cacheList.addAll(fetchCityListInteractor.fetchCityList())
////        if (cityList.size <= 2) cityList.add(city) else cityList[2] = city
////        cityList.add(userCity)
//        Log.d("Sem", "cacheList: $cacheList")
////        Log.d("Sem", "cityList: $cityList")
//    }

    fun fetchCitiesWeather() {
        _citiesWeather.value = ResultState.Loading()
//        updateCityList()
        viewModelScope.launch {
            cityList.forEach { city ->
                launch {
                    val result = fetchWeatherInteractor.fetchWeather(city)
                    if (result is ResultState.Success) {
                        _citiesWeather.value =
                            ResultState.Success(data = mutableListOf(result.data as WeatherModel))
//                        updateUserCity(city = city)
//                        when (city) {
//                            FIRST_CITY -> {}
//                            SECOND_CITY -> {}
//                            city -> {
//                                updateCityList(city = city)
//                            }
//                        }
                    } else {
                        _citiesWeather.value = ResultState.Error(
                            data = mutableListOf(result.data as WeatherModel),
                            message = result.message.toString()
                        )
                    }
                }
            }
        }
    }
}
