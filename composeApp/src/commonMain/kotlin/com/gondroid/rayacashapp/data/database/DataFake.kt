package com.gondroid.rayacashapp.data.database

import com.gondroid.rayacashapp.data.database.entity.BalanceEntity
import com.gondroid.rayacashapp.data.database.entity.TransactionEntity
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
    BalanceEntity(currency = USD.toString(), amount = "50000.00", updatedAt = ""),
    BalanceEntity(currency = BTC.toString(), amount = "0.0052565444", updatedAt = ""),
    BalanceEntity(currency = ETH.toString(), amount = "0.0010100203", updatedAt = "")
)