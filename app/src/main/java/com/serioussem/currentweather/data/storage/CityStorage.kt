package com.serioussem.currentweather.data.storage

interface CityStorage {
    fun fetchUserCity(): String
    fun updateUserCity(city: String)
}