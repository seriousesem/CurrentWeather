package com.serioussem.currentweather.data.di

import android.content.Context
import com.serioussem.currentweather.core.Constants.TIMEOUT
import com.serioussem.currentweather.core.Constants.TIMEUNIT
import com.serioussem.currentweather.core.Constants.WEATHER_URL
import com.serioussem.currentweather.data.cloud.WeatherApi
import com.serioussem.currentweather.data.core.NetworkInterceptor
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.data.core.exception.NoInternetException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(baseUrl)
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
        }.build()

    @Provides
    @Singleton
    fun provideOkHttpClient(
        networkInterceptor: NetworkInterceptor,
        timeout: Long,
        timeUnit: TimeUnit
    ): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(timeout, timeUnit)
        readTimeout(timeout, timeUnit)
        addInterceptor(networkInterceptor)
    }.build()

    @Provides
    @Singleton
    fun provideNetworkInterceptor(
        @ApplicationContext context: Context,
        noInternetException: NoInternetException
    ): NetworkInterceptor =
        NetworkInterceptor(context = context, noInternetException = noInternetException)

    @Provides
    @Singleton
    fun provideNoInternetException(resourceProvider: ResourceProvider): NoInternetException = NoInternetException(resourceProvider)

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideBaseUrl() = WEATHER_URL

    @Provides
    @Singleton
    fun provideTimeout() = TIMEOUT

    @Provides
    @Singleton
    fun provideTimeUnit() = TIMEUNIT

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): WeatherApi = retrofit.create(WeatherApi::class.java)

}