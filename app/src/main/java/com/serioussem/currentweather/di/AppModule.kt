package com.serioussem.currentweather.di


import com.serioussem.currentweather.data.datasource.local.room.RoomDataSource
import com.serioussem.currentweather.data.datasource.local.room.WeatherDao
import com.serioussem.currentweather.data.datasource.remote.retrofit.RetrofitDataSource
import com.serioussem.currentweather.data.datasource.remote.retrofit.WeatherApi
import com.serioussem.currentweather.data.core.InternetConnection
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.data.repository.WeatherRepositoryImpl
import com.serioussem.currentweather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideCloudDataSource(
        weatherApi: WeatherApi,
        resourceProvider: ResourceProvider
    ): RetrofitDataSource =
        RetrofitDataSource(weatherApi, resourceProvider)

    @Provides
    @Singleton
    fun provideCacheDataSource(
        weatherDao: WeatherDao,
        resourceProvider: ResourceProvider
    ): RoomDataSource =
        RoomDataSource(weatherDao = weatherDao, resourceProvider = resourceProvider)

    @Provides
    @Singleton
    fun provideWeatherRepository(
        retrofitDataSource: RetrofitDataSource,
        roomDataSource: RoomDataSource,
        internetConnection: InternetConnection,
        resourceProvider: ResourceProvider
    ): WeatherRepository =
        WeatherRepositoryImpl(
            retrofitDataSource = retrofitDataSource,
            roomDataSource = roomDataSource,
            internetConnection = internetConnection,
            resourceProvider = resourceProvider
        )
}