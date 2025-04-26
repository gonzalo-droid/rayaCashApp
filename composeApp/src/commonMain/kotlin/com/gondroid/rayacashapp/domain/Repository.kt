package com.gondroid.rayacashapp.domain

import com.gondroid.rayacashapp.shared.KMMDecimal
import com.gondroid.rayacashapp.domain.model.Balance
import com.gondroid.rayacashapp.domain.model.Transaction
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType

interface Repository {
    suspend fun getBalances(): List<Balance>
    suspend fun insertInitialData(): Unit
    suspend fun getConversionRatesToARS(): Result<Map<CurrencyType, KMMDecimal>>
    suspend fun updateBalance(balance: Balance)
    suspend fun getBalanceByCurrency(currency: String): Balance
    suspend fun getAllTransactions(): List<Transaction>
    suspend fun saveTransaction(transaction: Transaction)
}