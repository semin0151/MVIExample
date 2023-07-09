package com.example.mviexample.ui.main

import androidx.lifecycle.viewModelScope
import com.example.mviexample.AppConstants
import com.example.mviexample.base.BaseViewModel
import com.example.mviexample.base.SideEffect
import com.example.mviexample.util.channelToStateFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel: BaseViewModel<MainState, MainEvent, SideEffect>() {

    override var currentEvent: MainEvent = MainEvent.Init
    override val state = events.channelToStateFlow(MainState(), ::changeState, viewModelScope)
    override val sideEffect: Flow<SideEffect> = sideEffects.receiveAsFlow()

    override fun changeState(current: MainState, event: MainEvent): MainState {
        return when (event) {
            is MainEvent.Init -> current.copy(isLoading = false, isError = false)
            is MainEvent.Normal -> current.copy(isLoading = false, isError = false)
            is MainEvent.Loading -> current.copy(isLoading = true, isError = false)
            is MainEvent.Increment -> current.copy(count = current.count.plus(event.count), isLoading = false, isError = false)
            is MainEvent.Decrement -> current.copy(count = current.count.minus(event.count), isLoading = false, isError = false)
            is MainEvent.Error -> current.copy(isLoading = false, isError = true)
        }
    }

    fun onInitEvent() {
        viewModelScope.launch {
            onEvent(MainEvent.Normal)
        }
    }

    fun onIncrementEvent() {
        if (currentEvent == MainEvent.Loading) return

        viewModelScope.launch {
            onEvent(MainEvent.Loading)
            apiTest(true,
                onSuccess = {
                    onEvent(MainEvent.Increment())
                },
                onError = {

                })
        }
    }

    fun onDecrementEvent() {
        if (currentEvent == MainEvent.Loading) return

        viewModelScope.launch {
            onEvent(MainEvent.Loading)
            apiTest(true,
                onSuccess = {
                    onEvent(MainEvent.Decrement())
                },
                onError = {

                })
        }
    }

    fun onErrorEvent() {
        viewModelScope.launch {
            onEvent(MainEvent.Loading)
            onSideEffect(SideEffect.Toast("error!!"))
            apiTest(false,
                onSuccess = {

                },
                onError = {
                    onEvent(MainEvent.Error())
                })
        }
    }

    private suspend fun apiTest(testFlag: Boolean, onSuccess: suspend () -> Unit, onError: suspend () -> Unit) {
        delay(AppConstants.DELAY)
        if(testFlag) {
            onSuccess()
        }
        else {
            onError()
        }
    }
}
