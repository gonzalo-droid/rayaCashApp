package com.gondroid.rayacashapp.domain.model

data class Transaction(
    val id: String,
    val fromCurrency: Currency,
    val fromAmount: String,
    val toCurrency: Currency,
    val toAmount: String,
    val date: String,
    val status: TransactionStatus
)

enum class TransactionStatus {
    PENDING,
    COMPLETED,
    FAIL
}