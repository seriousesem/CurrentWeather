package com.serioussem.currentweather.data.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.serioussem.currentweather.data.core.exception.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(
    private val context: Context,
    private val noInternetException: NoInternetException
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (isConnected()) {
            throw noInternetException
        }
        return chain.proceed(chain.request().newBuilder().build())
    }


    fun isConnected(): Boolean {

        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val activeNetwork =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            result = when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        else -> false
                    }
                }
            }
        }
        return result
    }


}
