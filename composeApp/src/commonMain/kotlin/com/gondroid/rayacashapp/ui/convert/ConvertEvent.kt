package com.gondroid.rayacashapp.ui.convert


sealed interface ConvertEvent {
    data object Success : ConvertEvent
    data class Fail(val message: String) : ConvertEvent
}