package com.gondroid.rayacashapp.domain.model.balance

import com.gondroid.rayacashapp.domain.model.Transaction
import com.gondroid.rayacashapp.shared.createDecimal
import com.gondroid.rayacashapp.shared.plus
import com.gondroid.rayacashapp.shared.subtract
import com.gondroid.rayacashapp.shared.toPlainString

class DefaultBalanceUpdater : BalanceUpdater {
    override suspend fun update(transaction: Transaction, balances: List<Balance>): List<Balance> {
        val balanceFrom = balances.first { it.currency == transaction.fromCurrency }
        val balanceTo = balances.first { it.currency == transaction.toCurrency }

        val amountFrom = createDecimal(transaction.fromAmount)
        val amountTo = createDecimal(transaction.toAmount)

        val newBalanceFrom = balanceFrom.copy(
            amount = (createDecimal(balanceFrom.amount).subtract(amountFrom)).toPlainString()
        )
        val newBalanceTo = balanceTo.copy(
            amount = (createDecimal(balanceTo.amount).plus(amountTo)).toPlainString()
        )

        return listOf(newBalanceFrom, newBalanceTo)
    }
}
