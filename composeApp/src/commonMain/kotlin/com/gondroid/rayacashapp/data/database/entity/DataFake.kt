package com.gondroid.rayacashapp.data.database.entity

import com.gondroid.rayacashapp.data.database.generateUUID
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType.ARS
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType.BTC
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType.ETH
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType.USD

val fakeTransactionEntities = listOf(
    TransactionEntity(
        id = generateUUID(),
        fromCurrency = USD.toString(),
        fromAmount = "10.0",
        toCurrency = ARS.toString(),
        toAmount = "11759.20",
        date = "2025-04-22T10:32:12",
        status = "COMPLETED",
    ),

    TransactionEntity(
        id = generateUUID(),
        fromCurrency = USD.toString(),
        fromAmount = "100.0",
        toCurrency = BTC.toString(),
        toAmount = "0.00105654",
        date = "2025-04-13T09:40:20",
        status = "COMPLETED"
    )
)

val fakeBalanceEntities = listOf(
    BalanceEntity(currency = ARS.toString(), amount = "80000.00", updatedAt = ""),
    BalanceEntity(currency = USD.toString(), amount = "500.00", updatedAt = ""),
    BalanceEntity(currency = BTC.toString(), amount = "0.00525654", updatedAt = ""),
    BalanceEntity(currency = ETH.toString(), amount = "0.00100123", updatedAt = "")
)