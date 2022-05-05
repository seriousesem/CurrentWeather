package com.serioussem.currentweather.data.di

import android.content.Context
import com.serioussem.currentweather.data.cloud.ApiService
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
    companion object{
        private const val weatherUrl = "http://api.openweathermap.org/data/2.5/weather?appid=df407ee3089050448a58024e26abac06&lang=uk&units=metric"
        private const val timeout : Long = 30
        private val timeUnit = TimeUnit.SECONDS
    }

    @Provides
    @Singleton
    fun provideContext(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideBaseUrl() = weatherUrl

    @Provides
    @Singleton
    fun  provideTimeout() = timeout

    @Provides
    @Singleton
    fun provideTimeUnit() = timeUnit

    @Provides
    @Singleton
    fun provideOkHttpClient(timeout: Long, timeUnit: TimeUnit): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(timeout, timeUnit)
        .readTimeout(timeout, timeUnit)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, okHttpClient: OkHttpClient) : Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

}