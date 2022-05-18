package com.serioussem.currentweather.di


import com.serioussem.currentweather.data.cache.CacheDataSource
import com.serioussem.currentweather.data.cache.WeatherDao
import com.serioussem.currentweather.data.cloud.CloudDataSource
import com.serioussem.currentweather.data.cloud.WeatherApi
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
    ): CloudDataSource =
        CloudDataSource(weatherApi, resourceProvider)

    @Provides
    @Singleton
    fun provideCacheDataSource(
        weatherDao: WeatherDao,
        resourceProvider: ResourceProvider
    ): CacheDataSource =
        CacheDataSource(weatherDao = weatherDao, resourceProvider = resourceProvider)

    @Provides
    @Singleton
    fun provideWeatherRepository(
        cloudDataSource: CloudDataSource,
        cacheDataSource: CacheDataSource,
        internetConnection: InternetConnection,
        resourceProvider: ResourceProvider
    ): WeatherRepository =
        WeatherRepositoryImpl(
            cloudDataSource = cloudDataSource,
            cacheDataSource = cacheDataSource,
            internetConnection = internetConnection,
            resourceProvider = resourceProvider
        )
}