package com.gondroid.rayacashapp.util

import com.gondroid.rayacashapp.domain.Repository
import com.gondroid.rayacashapp.domain.model.balance.Balance
import com.gondroid.rayacashapp.domain.model.Transaction
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType
import com.gondroid.rayacashapp.shared.KMMDecimal

class FakeRepository : Repository {

    var saveTransactionResult: Boolean = true
    var balances = mutableListOf<Balance>()
    var conversions : Map<CurrencyType, KMMDecimal> = emptyMap()

    override suspend fun getBalances(): List<Balance> = balances
    override suspend fun insertInitialData() {}
    override suspend fun getConversionRatesToARS(): Result<Map<CurrencyType, KMMDecimal>> =
        Result.success(conversions)

    override suspend fun updateBalances(balances: List<Balance>): Boolean {
        balances.forEach { updatedBalance ->
            val index = this.balances.indexOfFirst { it.currency == updatedBalance.currency }
            if (index != -1) {
                this.balances[index] = updatedBalance
            }
        }
        return true
    }
    override suspend fun getBalanceByCurrency(currency: String): Balance =
        balances.first { it.currency.name == currency }

    override suspend fun getAllTransactions(): List<Transaction> = emptyList()
    override suspend fun saveTransaction(transaction: Transaction): Boolean = saveTransactionResult
    override suspend fun getBalanceByCurrencies(currencies: List<String>): List<Balance> = balances
}
