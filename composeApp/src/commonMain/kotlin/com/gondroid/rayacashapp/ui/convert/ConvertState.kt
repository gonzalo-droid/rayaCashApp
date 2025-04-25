package com.gondroid.rayacashapp.ui.convert

import androidx.compose.foundation.text.input.TextFieldState
import com.gondroid.rayacashapp.domain.model.Coin
import com.gondroid.rayacashapp.domain.model.coinsList

data class ConvertState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = coinsList,
    val fromCoin: TextFieldState = TextFieldState(),
    val toCoin: TextFieldState = TextFieldState(),
    val canConvert: Boolean = false,
)