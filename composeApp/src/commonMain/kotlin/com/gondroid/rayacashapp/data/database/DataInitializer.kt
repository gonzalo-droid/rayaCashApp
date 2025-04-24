package com.gondroid.rayacashapp.data.database

import com.gondroid.rayacashapp.data.database.dao.BalanceDAO
import com.gondroid.rayacashapp.data.database.entity.BalanceEntity
import com.gondroid.rayacashapp.domain.model.Currency.ARS
import com.gondroid.rayacashapp.domain.model.Currency.BTC
import com.gondroid.rayacashapp.domain.model.Currency.ETH
import com.gondroid.rayacashapp.domain.model.Currency.USD

class DataInitializer(private val balanceDao: BalanceDAO) {

    suspend fun insertInitialBalancesIfNeeded() {
        val existing = balanceDao.getAllBalances()
        if (existing.isEmpty()) {
            val initialBalances = listOf(
                BalanceEntity(currency = ARS.toString(), amount = 123.0, updatedAt = ""),
                BalanceEntity(currency = USD.toString(), amount = 500.0, updatedAt = ""),
                BalanceEntity(currency = BTC.toString(), amount = 0.0123321, updatedAt = ""),
                BalanceEntity(currency = ETH.toString(), amount = 0.2043115, updatedAt = "")
            )
            balanceDao.insertBalances(initialBalances)
        }
    }
}

