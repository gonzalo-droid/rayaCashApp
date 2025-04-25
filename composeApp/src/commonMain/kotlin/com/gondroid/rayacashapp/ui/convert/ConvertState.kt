package com.gondroid.rayacashapp.ui.convert

import androidx.compose.foundation.text.input.TextFieldState
import com.gondroid.rayacashapp.domain.model.Coin
import com.gondroid.rayacashapp.domain.model.coinsList

data class ConvertState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = coinsList,
    val fromCoinField: TextFieldState = TextFieldState(),
    val toCoinField: TextFieldState = TextFieldState(),
    val fromCoinSelected: Coin = coinsList[0],
    val toCoinSelected: Coin = coinsList[1],
    val canConvert: Boolean = false,
)