package com.example.mviexample.main

import com.example.mviexample.model.A
import com.example.mviexample.model.B

data class MainState(
    val a: A = A(),
    val b: B = B(),
    val count: Int = 0,
    val isLoading: Boolean = false,
    val isError: Boolean = false
)