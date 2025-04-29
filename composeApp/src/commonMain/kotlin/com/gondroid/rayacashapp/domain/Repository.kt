package com.gondroid.rayacashapp.domain

import com.gondroid.rayacashapp.domain.model.balance.Balance
import com.gondroid.rayacashapp.domain.model.Transaction
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType
import com.gondroid.rayacashapp.shared.KMMDecimal

interface Repository {
    suspend fun getBalances(): List<Balance>
    suspend fun insertInitialData(): Unit
    suspend fun getConversionRatesToARS(): Result<Map<CurrencyType, KMMDecimal>>
    suspend fun updateBalances(balances: List<Balance>): Boolean
    suspend fun getBalanceByCurrency(currency: String): Balance
    suspend fun getAllTransactions(): List<Transaction>
    suspend fun saveTransaction(transaction: Transaction): Boolean
    suspend fun getBalanceByCurrencies(currencies: List<String>): List<Balance>
}