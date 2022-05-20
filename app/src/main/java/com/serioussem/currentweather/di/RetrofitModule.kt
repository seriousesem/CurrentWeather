package com.serioussem.currentweather.di

import com.serioussem.currentweather.utils.Constants.WEATHER_URL
import com.serioussem.currentweather.data.datasource.remote.retrofit.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(30, TimeUnit.SECONDS)
        readTimeout(30, TimeUnit.SECONDS)
    }.build()

    @Provides
    @Singleton
    fun provideRetrofitService(okHttpClient: OkHttpClient): RetrofitService {
        return Retrofit.Builder().apply {
            baseUrl(WEATHER_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(okHttpClient)
        }.build()
            .create(RetrofitService::class.java)
    }
}