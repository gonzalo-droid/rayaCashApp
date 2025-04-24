package com.gondroid.rayacashapp.ui.home

import com.gondroid.rayacashapp.domain.model.Balance

data class HomeState(
    val isLoading: Boolean = false,
    val totalBalance: String = "",
    val balances: List<Balance> = listOf<Balance>()
)