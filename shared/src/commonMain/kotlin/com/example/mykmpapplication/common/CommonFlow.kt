package com.example.mykmpapplication.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun interface Closeable {
    fun close()
}

@Suppress("OPT_IN_USAGE")
class CommonFlow<T>(private val origin: Flow<T>) : Flow<T> by origin {
    fun watch(block: (T) -> Unit): Closeable {
        val job = origin.onEach { block(it) }
            .launchIn(CoroutineScope(Dispatchers.Main))
        return Closeable { job.cancel() }
    }
}

@Suppress("OPT_IN_USAGE")
class CommonStateFlow<T>(private val origin: StateFlow<T>) : StateFlow<T> by origin {
    fun watch(block: (T) -> Unit): Closeable {
        val job = origin.onEach { block(it) }
            .launchIn(CoroutineScope(Dispatchers.Main))
        return Closeable { job.cancel() }
    }
}

fun <T> Flow<T>.asCommonFlow(): CommonFlow<T> = CommonFlow(this)

fun <T> StateFlow<T>.asCommonStateFlow(): CommonStateFlow<T> = CommonStateFlow(this)
