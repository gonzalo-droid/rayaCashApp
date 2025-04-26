package com.gondroid.rayacashapp.domain.model

import com.gondroid.rayacashapp.data.database.entity.TransactionEntity
import com.gondroid.rayacashapp.data.database.generateUUID
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType

data class Transaction(
    val id: String = generateUUID(),
    val fromCurrency: CurrencyType,
    val fromAmount: String,
    val toCurrency: CurrencyType,
    val toAmount: String,
    val date: String = getCurrentLocalTime(),
    val status: TransactionStatus = TransactionStatus.COMPLETED
) {
    fun toEntity(): TransactionEntity {
        return TransactionEntity(
            id = id,
            fromCurrency = fromCurrency.name,
            fromAmount = fromAmount,
            toCurrency = toCurrency.name,
            toAmount = toAmount,
            date = date,
            status = status.name
        )
    }
}

enum class TransactionStatus {
    PENDING,
    COMPLETED,
    FAIL
}