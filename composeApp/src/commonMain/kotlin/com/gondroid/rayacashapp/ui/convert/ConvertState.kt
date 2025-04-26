package com.gondroid.rayacashapp.ui.convert

import androidx.compose.foundation.text.input.TextFieldState
import com.gondroid.rayacashapp.shared.KMMDecimal
import com.gondroid.rayacashapp.domain.model.Balance
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType
import com.gondroid.rayacashapp.domain.model.convertRate.currencyList

data class ConvertState(
    val isLoading: Boolean = false,
    val coins: List<CurrencyType> = currencyList,
    val fromCoinField: TextFieldState = TextFieldState(),
    val fromCoinSelected: CurrencyType = currencyList[0],
    val toCoinSelected: CurrencyType = currencyList[1],
    val canConvert: Boolean = false,
    val balance: Balance = Balance(),
    val amountConverted: String = "0",
    val conversionRates: Map<CurrencyType, KMMDecimal> = emptyMap()
)