package com.example.mviexample.main

sealed class MainEvent {
    object Init : MainEvent()
    object Normal : MainEvent()
    object Loading : MainEvent()
    data class Increment(val count : Int = 1) : MainEvent()
    data class Decrement(val count : Int = 1) : MainEvent()
    data class Error(val exception: Exception? = null) : MainEvent()
}