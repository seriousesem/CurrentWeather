package com.serioussem.currentweather.data.di


import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.data.core.exception.NoInternetException
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
    fun provideNoInternetException(resourceProvider: ResourceProvider): NoInternetException = NoInternetException(resourceProvider)

}