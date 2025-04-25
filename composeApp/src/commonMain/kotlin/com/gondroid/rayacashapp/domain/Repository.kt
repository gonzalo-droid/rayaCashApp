package com.gondroid.rayacashapp.domain

import com.gondroid.rayacashapp.KMMDecimal
import com.gondroid.rayacashapp.domain.model.Balance
import com.gondroid.rayacashapp.domain.model.Transaction

interface Repository {
    suspend fun getBalances(): List<Balance>
    suspend fun insertInitialData(): Unit
    suspend fun getConversionRatesToARS(ids: List<String>): Result<Map<String, KMMDecimal>>
    suspend fun updateBalance(balance: Balance)
    suspend fun getBalanceByCurrency(currency: String): Balance
    suspend fun getAllTransactions(): List<Transaction>
}