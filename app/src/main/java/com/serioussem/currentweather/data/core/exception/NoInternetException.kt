package com.serioussem.currentweather.data.core.exception

import com.serioussem.currentweather.R
import com.serioussem.currentweather.data.core.ResourceProvider
import java.io.IOException

class NoInternetException(private val resourceProvider: ResourceProvider): IOException() {
    override val message: String
        get() = resourceProvider.string(R.string.no_internet_connection_message)
}