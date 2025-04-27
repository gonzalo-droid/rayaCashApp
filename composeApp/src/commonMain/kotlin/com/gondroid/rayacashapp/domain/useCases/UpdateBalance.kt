package com.gondroid.rayacashapp.domain.useCases

import com.gondroid.rayacashapp.shared.KMMDecimal
import com.gondroid.rayacashapp.shared.createDecimal
import com.gondroid.rayacashapp.domain.Repository
import com.gondroid.rayacashapp.domain.model.Balance
import com.gondroid.rayacashapp.domain.model.BalanceAmountToARS
import com.gondroid.rayacashapp.domain.model.Transaction
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType
import com.gondroid.rayacashapp.shared.plus
import com.gondroid.rayacashapp.shared.roundToDecimal
import com.gondroid.rayacashapp.shared.subtract
import com.gondroid.rayacashapp.shared.times
import com.gondroid.rayacashapp.shared.toPlainString

class UpdateBalance(private val repository: Repository) {

    suspend operator fun invoke(transaction: Transaction): Result<Boolean> {
        return try {
            val result = repository.saveTransaction(transaction)
            if (result) {
                val currencyFrom = transaction.fromCurrency
                val currencyTo = transaction.toCurrency
                val amountFrom = createDecimal(transaction.fromAmount)
                val amountTo = createDecimal(transaction.toAmount)

                val currencies = listOf(currencyFrom.name, currencyTo.name)
                val balances = repository.getBalanceByCurrencies(currencies)

                val balanceFrom = balances.first { it.currency == currencyFrom }
                val balanceTo = balances.first { it.currency == currencyTo }

                val newBalanceFrom = balanceFrom.copy(
                    amount = (createDecimal(balanceFrom.amount).subtract(amountFrom)).toPlainString()
                )
                val newBalanceTo = balanceTo.copy(
                    amount = (createDecimal(balanceTo.amount).plus(amountTo)).toPlainString()
                )

                val newBalances = repository.updateBalances(listOf(newBalanceFrom, newBalanceTo))

                Result.success(newBalances)
            } else {
                Result.failure( Exception("Error al guardar la transacción"))
            }
        } catch (e: Exception) {
            println("Excepción durante la conversión: ${e.message}")
            Result.failure(e)
        }
    }
}