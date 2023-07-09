package com.example.mviexample.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<State, Event, SideEffect>: ViewModel() {
    protected abstract var currentEvent: Event
    protected val events = Channel<Event>()
    protected val sideEffects = Channel<SideEffect>()
    abstract val state: StateFlow<State>
    abstract val sideEffect: Flow<SideEffect>

    protected suspend fun onEvent(event: Event) {
        currentEvent = event
        events.send(event)
    }

    protected abstract fun changeState(current: State, event: Event): State

    protected suspend fun onSideEffect(sideEffect: SideEffect) {
        sideEffects.send(sideEffect)
    }
}