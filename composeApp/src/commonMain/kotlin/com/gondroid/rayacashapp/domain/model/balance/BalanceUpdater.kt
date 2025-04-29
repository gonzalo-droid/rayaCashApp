package com.gondroid.rayacashapp.domain.model.balance

import com.gondroid.rayacashapp.domain.model.Transaction

interface BalanceUpdater {
    suspend fun update(transaction: Transaction, balances: List<Balance>): List<Balance>
}
