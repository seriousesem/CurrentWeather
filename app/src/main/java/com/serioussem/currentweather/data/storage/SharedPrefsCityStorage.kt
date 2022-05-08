package com.serioussem.currentweather.data.storage

import android.content.Context
import com.serioussem.currentweather.core.Constants.KEY_USER_CITY
import com.serioussem.currentweather.core.Constants.SHARED_PREFS_CITY

import javax.inject.Inject


class SharedPrefsCityStorage@Inject constructor(context: Context) : CityStorage {

    private val userCitiSharedPreferences =
        context.getSharedPreferences(SHARED_PREFS_CITY, Context.MODE_PRIVATE)


    override fun fetchUserCity(): String =
        userCitiSharedPreferences.getString(KEY_USER_CITY, "") ?: ""


    override fun updateUserCity(city: String) =
        userCitiSharedPreferences.edit().putString(KEY_USER_CITY, city).apply()

}
