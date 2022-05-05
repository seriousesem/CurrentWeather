package com.serioussem.currentweather.core

import android.content.Context
import androidx.annotation.StringRes
import javax.inject.Inject

interface ResourceProvider {

    fun string(@StringRes id:Int): String
    fun string(@StringRes id: Int, vararg args: Any): String

    class Base @Inject constructor(private val context: Context): ResourceProvider{

        override fun string(id: Int): String = context.getString(id)

        override fun string(id: Int, vararg args: Any): String = context.getString(id, args)
    }
}