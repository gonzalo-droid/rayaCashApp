package com.gondroid.rayacashapp.ui.convert

import com.gondroid.rayacashapp.domain.model.Coin


sealed interface ConvertScreenAction {
    data object Back : ConvertScreenAction
    data object GoToPreview : ConvertScreenAction
    data class CoinFrom(val coin: Coin) : ConvertScreenAction
    data class CoinTo(val coin: Coin) : ConvertScreenAction
    data class FilterCoin(val isCoinFrom: Boolean) : ConvertScreenAction
}