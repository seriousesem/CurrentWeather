package com.serioussem.currentweather.data.di


import com.serioussem.currentweather.data.cloud.CloudDataSource
import com.serioussem.currentweather.data.cloud.WeatherApi
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.data.core.exception.NoInternetException
import com.serioussem.currentweather.data.repository.WeatherRepositoryImpl
import com.serioussem.currentweather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CoreModule {

    @Provides
    @Singleton
    fun provideNoInternetException(resourceProvider: ResourceProvider): NoInternetException =
        NoInternetException(resourceProvider)
    @Provides
    @Singleton
    fun provideCloudDataSource(weatherApi: WeatherApi): CloudDataSource =
        CloudDataSource(weatherApi)

    @Provides
    @Singleton
    fun provideWeatherRepository(cloudDataSource: CloudDataSource): WeatherRepository =
        WeatherRepositoryImpl(cloudDataSource)

}