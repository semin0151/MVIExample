package com.example.mviexample.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*


fun <Event, State> Channel<Event>.channelToStateFlow(state: State, block: (State, Event) -> State, scope: CoroutineScope): StateFlow<State> {
    return receiveAsFlow().runningFold(state, block).stateIn(scope, SharingStarted.Eagerly, state)
}