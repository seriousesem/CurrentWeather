package com.serioussem.currentweather.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


class Dispatchers @Inject constructor(){
    fun launchUI(
        scope: CoroutineScope,
        block: suspend CoroutineScope.() -> Unit
    ) = scope.launch(context = Main, block = block)

    fun launchBackground(
        scope: CoroutineScope,
        block: suspend CoroutineScope.() -> Unit
    ) = scope.launch(context = IO, block = block)

    fun launchDefault(
        scope: CoroutineScope,
        block: suspend CoroutineScope.() -> Unit
    ) = scope.launch(context = Default, block = block)

    suspend fun changeToUi(block: suspend CoroutineScope.() -> Unit) {
        withContext(context = Main, block = block)
    }

}
