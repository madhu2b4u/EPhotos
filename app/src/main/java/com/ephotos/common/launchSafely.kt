package com.ephotos.common

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun CoroutineScope.launchSafely(
    handler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Log.e("LaunchSafely", "Exception thrown", throwable)
    },
    block: suspend CoroutineScope.() -> Unit
): Job = launch(handler) { block() }