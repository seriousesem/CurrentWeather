package com.serioussem.currentweather.di

import android.content.Context
import androidx.room.Room
import com.serioussem.currentweather.utils.Constants.DATABASE_NAME
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

    @Provides
    @Singleton
    fun provideWeatherDataBase(
        @ApplicationContext context: Context
    ): WeatherDataBase = Room.databaseBuilder(
        context,
        WeatherDataBase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideWeatherDao(weatherDataBase: WeatherDataBase): WeatherDao =
        weatherDataBase.weatherDao()
}