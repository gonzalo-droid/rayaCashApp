package com.gondroid.rayacashapp.ui.home

import com.gondroid.rayacashapp.domain.model.balance.Balance

data class HomeState(
    val isLoading: Boolean = false,
    val totalBalance: String = "ARS 0.0O",
    val balances: List<Balance> = emptyList()
)