package com.gondroid.rayacashapp.ui.convert

import androidx.compose.foundation.text.input.TextFieldState
import com.gondroid.rayacashapp.KMMDecimal
import com.gondroid.rayacashapp.domain.model.Balance
import com.gondroid.rayacashapp.domain.model.Coin
import com.gondroid.rayacashapp.domain.model.coinsList
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType

data class ConvertState(
    val isLoading: Boolean = false,
    val coins: List<Coin> = coinsList,
    val fromCoinField: TextFieldState = TextFieldState(),
    val fromCoinSelected: Coin = coinsList[0],
    val toCoinSelected: Coin = coinsList[1],
    val canConvert: Boolean = false,
    val balance: Balance = Balance(),
    val amountConverted: String = "0",
    val conversionRates: Map<CurrencyType, KMMDecimal> = emptyMap()
)