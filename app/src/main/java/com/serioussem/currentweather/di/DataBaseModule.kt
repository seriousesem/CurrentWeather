package com.serioussem.currentweather.di

import android.content.Context
import androidx.room.Room
import com.serioussem.currentweather.utils.Constants.DATABASE_NAME
import com.serioussem.currentweather.data.datasource.local.room.WeatherDao
import com.serioussem.currentweather.data.datasource.local.room.RoomDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataBaseModule {

    @Provides
    @Singleton
    fun provideWeatherDataBase(
        @ApplicationContext context: Context
    ): RoomDataBase = Room.databaseBuilder(
        context,
        RoomDataBase::class.java,
        DATABASE_NAME
    ).build()

    @Provides
    @Singleton
    fun provideWeatherDao(roomDataBase: RoomDataBase): WeatherDao =
        roomDataBase.weatherDao()
}