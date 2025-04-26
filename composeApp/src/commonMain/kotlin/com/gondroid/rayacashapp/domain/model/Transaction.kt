package com.gondroid.rayacashapp.domain.model

import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType

data class Transaction(
    val id: String,
    val fromCurrency: CurrencyType,
    val fromAmount: String,
    val toCurrency: CurrencyType,
    val toAmount: String,
    val date: String,
    val status: TransactionStatus
)

enum class TransactionStatus {
    PENDING,
    COMPLETED,
    FAIL
}