package com.gondroid.rayacashapp.data

import com.gondroid.rayacashapp.data.database.RayaCashDatabase
import com.gondroid.rayacashapp.data.database.entity.BalanceEntity
import com.gondroid.rayacashapp.data.remote.ApiService
import com.gondroid.rayacashapp.domain.Repository
import com.gondroid.rayacashapp.domain.model.Balance
import com.gondroid.rayacashapp.domain.model.Currency.ARS
import com.gondroid.rayacashapp.domain.model.Currency.BTC
import com.gondroid.rayacashapp.domain.model.Currency.ETH
import com.gondroid.rayacashapp.domain.model.Currency.USD

class RepositoryImpl(
    private val apiService: ApiService,
    private val database: RayaCashDatabase
) : Repository {
    override suspend fun getBalances(): List<Balance> {
        return database.getBalanceDao().getAllBalances().map { it.toDomain() }
    }

    override suspend fun insertInitialBalances() {
        val existing = database.getBalanceDao().getAllBalances()
        if (existing.isEmpty()) {
            val initialBalances = listOf(
                BalanceEntity(currency = ARS.toString(), amount = 123.0, updatedAt = ""),
                BalanceEntity(currency = USD.toString(), amount = 500.0, updatedAt = ""),
                BalanceEntity(currency = BTC.toString(), amount = 0.0123321, updatedAt = ""),
                BalanceEntity(currency = ETH.toString(), amount = 0.2043115, updatedAt = "")
            )
            database.getBalanceDao().insertBalances(initialBalances)
        }
    }
}