package com.serioussem.currentweather.data.di


import android.content.Context
import com.serioussem.currentweather.data.cache.CacheDataSource
import com.serioussem.currentweather.data.cache.WeatherDao
import com.serioussem.currentweather.data.cloud.CloudDataSource
import com.serioussem.currentweather.data.cloud.WeatherApi
import com.serioussem.currentweather.data.core.NetworkInterceptor
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.data.repository.WeatherRepositoryImpl
import com.serioussem.currentweather.data.storage.SharedPrefsCityStorage
import com.serioussem.currentweather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    @Singleton
    fun provideResourceProvider(@ApplicationContext context: Context): ResourceProvider =
        ResourceProvider(context = context)

    @Provides
    @Singleton
    fun provideCloudDataSource(
        weatherApi: WeatherApi,
        networkInterceptor: NetworkInterceptor,
        resourceProvider: ResourceProvider
    ): CloudDataSource =
        CloudDataSource(weatherApi, networkInterceptor, resourceProvider)

    @Provides
    @Singleton
    fun provideCacheDataSource(weatherDao: WeatherDao): CacheDataSource =
        CacheDataSource(weatherDao = weatherDao)

    @Provides
    @Singleton
    fun provideWeatherRepository(
        cloudDataSource: CloudDataSource,
        cacheDataSource: CacheDataSource,
        storage: SharedPrefsCityStorage,
        resourceProvider: ResourceProvider
    ): WeatherRepository =
        WeatherRepositoryImpl(
            cloudDataSource = cloudDataSource,
            cacheDataSource = cacheDataSource,
            storage = storage,
            resourceProvider = resourceProvider
        )

}