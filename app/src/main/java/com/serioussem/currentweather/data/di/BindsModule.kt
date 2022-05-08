package com.serioussem.currentweather.data.di


import com.serioussem.currentweather.data.core.ResourceProvider
import com.serioussem.currentweather.data.core.ResponseHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BindsModule {

    @Binds
    abstract fun bindResponseHandler(responseHandler: ResponseHandler.Base): ResponseHandler

}