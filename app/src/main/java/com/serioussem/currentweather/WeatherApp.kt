package com.serioussem.currentweather

import android.app.Application
import com.serioussem.currentweather.data.net.WeatherService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class WeatherApp : Application() {

    private  companion object{
        const val BASE_URL = "http://api.openweathermap.org/data/2.5/weather?appid=df407ee3089050448a58024e26abac06&lang=uk&units=metric"
    }

    override fun onCreate() {
        super.onCreate()

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        val service = retrofit.create(WeatherService::class.java)

    }
}