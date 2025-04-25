package com.gondroid.rayacashapp.ui.transaction

import com.gondroid.rayacashapp.domain.model.Transaction


data class TransactionState(
    val isLoading: Boolean = false,
    val transactions: List<Transaction> = emptyList()
)