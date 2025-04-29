package com.gondroid.rayacashapp.domain.useCases

import com.gondroid.rayacashapp.domain.Repository
import com.gondroid.rayacashapp.domain.model.Transaction
import com.gondroid.rayacashapp.domain.model.balance.BalanceUpdater

class UpdateBalance(
    private val repository: Repository,
) {
    suspend operator fun invoke(
        transaction: Transaction,
        balanceUpdater: BalanceUpdater
    ): Result<Boolean> {
        return try {
            val result = repository.saveTransaction(transaction)
            if (result) {

                val currencies = listOf(
                    transaction.fromCurrency.name,
                    transaction.toCurrency.name
                )

                val balances = repository.getBalanceByCurrencies(currencies)
                val updatedBalances = balanceUpdater.update(transaction, balances)
                val newBalancesSaved = repository.updateBalances(updatedBalances)

                Result.success(newBalancesSaved)
            } else {
                Result.failure(Exception("Error al guardar la transacción"))
            }
        } catch (e: Exception) {
            println("Excepción durante la conversión: ${e.message}")
            Result.failure(e)
        }
    }
}