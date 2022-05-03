package com.serioussem.currentweather.data.di

import android.content.Context
import androidx.room.Room
import com.serioussem.currentweather.data.cache.WeatherDao
import com.serioussem.currentweather.data.cache.WeatherDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    companion object {
        private const val WeatherDataBaseName = "weather.db"
    }

    @Provides
    @Singleton
    fun provideDataBaseName() = WeatherDataBaseName

    @Provides
    @Singleton
    fun provideWeatherDataBase(
        @ApplicationContext context: Context,
        dataBaseName: String
    ): WeatherDataBase = Room.databaseBuilder(
        context,
        WeatherDataBase::class.java,
        dataBaseName
    ).build()

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDataBase: WeatherDataBase): WeatherDao = weatherDataBase.weatherDao()

}