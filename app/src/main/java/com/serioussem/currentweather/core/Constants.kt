package com.serioussem.currentweather.core

import java.util.concurrent.TimeUnit

object Constants {
    const val WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?appid=df407ee3089050448a58024e26abac06&lang=uk&units=metric"
    const val FIRST_CITY = "Київ"
    const val SECOND_CITY = "Львів"
    const val TIMEOUT: Long = 30
    val TIMEUNIT = TimeUnit.SECONDS
}