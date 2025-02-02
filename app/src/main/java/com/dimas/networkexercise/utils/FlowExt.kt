package com.dimas.networkexercise.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun <T> Flow<T>.observeIn(
    owner: LifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    action: (T) -> Unit
) {
    this
        .flowWithLifecycle(owner.lifecycle, state)
        .distinctUntilChanged()
        .onEach { action(it) }
        .launchIn(owner.lifecycleScope)
}