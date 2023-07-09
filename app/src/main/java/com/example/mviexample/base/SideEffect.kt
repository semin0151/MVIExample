package com.example.mviexample.base

sealed class SideEffect {
    data class Toast(val text: String) : SideEffect()
    data class Dialog(val title: String, val content: String) : SideEffect()
}
