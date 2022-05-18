package com.serioussem.currentweather.di


import com.serioussem.currentweather.data.cache.CacheDataSource
import com.serioussem.currentweather.data.cache.WeatherDao
import com.serioussem.currentweather.data.cloud.CloudDataSource
import com.serioussem.currentweather.data.cloud.WeatherApi
import com.serioussem.currentweather.data.core.ResourceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ProvideModule {

    @Provides
    @Singleton
    fun provideCloudDataSource(
        weatherApi: WeatherApi,
        resourceProvider: ResourceProvider
    ): CloudDataSource =
        CloudDataSource(weatherApi, resourceProvider)

    @Provides
    @Singleton
    fun provideCacheDataSource(weatherDao: WeatherDao, resourceProvider: ResourceProvider): CacheDataSource =
        CacheDataSource(weatherDao = weatherDao, resourceProvider = resourceProvider)

}