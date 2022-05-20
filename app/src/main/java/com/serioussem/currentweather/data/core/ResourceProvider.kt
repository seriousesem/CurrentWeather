package com.serioussem.currentweather.data.core

import android.content.Context
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ResourceProvider @Inject constructor(@ApplicationContext private val context: Context) {

    fun string(@StringRes id: Int): String = context.getString(id)

    fun string(@StringRes id: Int, vararg args: Any): String = context.getString(id, args)

}