package com.example.newsapp.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

fun <T> Flow<T>.likeStateFlow(
    scope: CoroutineScope,
    initialValue: T,
): StateFlow<T> = this.stateIn(
    scope,
    started = SharingStarted.WhileSubscribed(5_000),
    initialValue,
)