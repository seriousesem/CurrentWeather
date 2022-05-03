package com.serioussem.currentweather

import android.app.Application
import com.serioussem.currentweather.data.cache.CacheDataSource

class WeatherApp : Application() {

    lateinit var cacheDataSource: CacheDataSource

    override fun onCreate() {
        super.onCreate()

        cacheDataSource = CacheDataSource.Base(context = this)

    }
}