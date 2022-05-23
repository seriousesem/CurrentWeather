package com.serioussem.currentweather.di

import com.serioussem.currentweather.data.core.InternetConnection
import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.data.datasource.cache.room.RoomDataSource
import com.serioussem.currentweather.data.datasource.cache.room.WeatherDao
import com.serioussem.currentweather.data.datasource.mappers.ApiModelToDataMapper
import com.serioussem.currentweather.data.datasource.mappers.DataResultToDomainMapper
import com.serioussem.currentweather.data.datasource.remote.retrofit.RetrofitDataSource
import com.serioussem.currentweather.data.datasource.remote.retrofit.RetrofitService
import com.serioussem.currentweather.data.repository.WeatherRepositoryImpl
import com.serioussem.currentweather.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun provideRetrofitDataSource(
        service: RetrofitService,
        mapper: ApiModelToDataMapper,
        resourceProvider: ResourceProvider,
        internetConnection: InternetConnection
    ): RetrofitDataSource =
        RetrofitDataSource(
            service = service,
            mapper = mapper,
            resourceProvider = resourceProvider,
            internetConnection = internetConnection
        )

    @Provides
    fun provideRoomDataSource(
        dao: WeatherDao,
        resourceProvider: ResourceProvider
    ): RoomDataSource =
        RoomDataSource(
            dao = dao,
            resourceProvider = resourceProvider
        )

    @Provides
    fun provideWeatherRepository(
        retrofitDataSource: RetrofitDataSource,
        roomDataSource: RoomDataSource,
        mapper: DataResultToDomainMapper
    ): WeatherRepository =
        WeatherRepositoryImpl(
            retrofitDataSource = retrofitDataSource,
            roomDataSource = roomDataSource,
            mapper = mapper
        )
}