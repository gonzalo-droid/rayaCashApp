package com.gondroid.rayacashapp.ui.convert

import androidx.compose.foundation.text.input.TextFieldState

data class ConvertState(
    val isLoading: Boolean = false,
    val fromCoin: TextFieldState = TextFieldState(),
    val toCoin: TextFieldState = TextFieldState(),
    val canConvert: Boolean = false,
)