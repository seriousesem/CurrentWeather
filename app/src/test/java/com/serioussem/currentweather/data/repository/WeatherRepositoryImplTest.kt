package com.serioussem.currentweather.data.repository

import android.util.Log
import com.serioussem.currentweather.utils.Constants
import org.junit.Assert.*

import org.junit.Test

class WeatherRepositoryImplTest {

    private var defaultCityList: MutableList<String> =
        mutableListOf(Constants.FIRST_CITY, Constants.SECOND_CITY)
    private var cacheCityList: MutableList<String> = mutableListOf("Канів", "Буча", "Шпола")
    private var cityList: MutableList<String> = defaultCityList

    @Test
    fun saveUserCity() {

    }

    @Test
    fun fetchWeather() {
    }
    @Test
    fun updateCityList() {
        when {
            (cacheCityList.size < 3 || cityList.size == 3) -> cityList
            else -> {
                cityList.add(cacheCityList.last())
                cityList
            }
        }

    }
}