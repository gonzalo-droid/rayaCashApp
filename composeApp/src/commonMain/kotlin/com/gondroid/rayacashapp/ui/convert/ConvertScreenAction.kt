package com.gondroid.rayacashapp.ui.convert

import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType


sealed interface ConvertScreenAction {
    data object Back : ConvertScreenAction
    data object SaveTransaction : ConvertScreenAction
    data class CurrencyFrom(val currency: CurrencyType) : ConvertScreenAction
    data class CurrencyTo(val currency: CurrencyType) : ConvertScreenAction
    data class FilterCurrency(val isCoinFrom: Boolean) : ConvertScreenAction
}