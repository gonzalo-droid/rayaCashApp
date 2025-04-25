package com.gondroid.rayacashapp.data

import com.gondroid.rayacashapp.KMMDecimal
import com.gondroid.rayacashapp.createDecimal
import com.gondroid.rayacashapp.data.database.RayaCashDatabase
import com.gondroid.rayacashapp.data.database.entity.BalanceEntity
import com.gondroid.rayacashapp.data.database.entity.TransactionEntity
import com.gondroid.rayacashapp.data.remote.ApiService
import com.gondroid.rayacashapp.domain.Repository
import com.gondroid.rayacashapp.domain.model.Balance
import com.gondroid.rayacashapp.domain.model.Currency.ARS
import com.gondroid.rayacashapp.domain.model.Currency.BTC
import com.gondroid.rayacashapp.domain.model.Currency.ETH
import com.gondroid.rayacashapp.domain.model.Currency.USD
import com.gondroid.rayacashapp.domain.model.Transaction
import kotlinx.serialization.json.contentOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

class RepositoryImpl(
    private val apiService: ApiService,
    private val database: RayaCashDatabase
) : Repository {
    override suspend fun getBalances(): List<Balance> {
        return database.getBalanceDao().getAllBalances().map { it.toDomain() }
    }

    override suspend fun insertInitialData() {
        val existing = database.getBalanceDao().getAllBalances()
        if (existing.isEmpty()) {
            val initialBalances = listOf(
                BalanceEntity(currency = ARS.toString(), amount = "80000.0", updatedAt = ""),
                BalanceEntity(currency = USD.toString(), amount = "500.0", updatedAt = ""),
                BalanceEntity(currency = BTC.toString(), amount = "0.00525654", updatedAt = ""),
                BalanceEntity(currency = ETH.toString(), amount = "0.00100123", updatedAt = "")
            )
            database.getBalanceDao().insertBalances(initialBalances)

            val transactions = listOf(
                TransactionEntity(
                    fromCurrency = USD.toString(),
                    fromAmount = "10.0",
                    toCurrency = ARS.toString(),
                    toAmount = "11759.20",
                    date = "2025-04-22T10:00:00",
                    status = "COMPLETED"
                ),

                TransactionEntity(
                    fromCurrency = USD.toString(),
                    fromAmount = "100.0",
                    toCurrency = BTC.toString(),
                    toAmount = "0.00105654",
                    date = "2025-04-22T10:00:00",
                    status = "COMPLETED"
                )
            )

            database.getTransactionDao().insertTransactions(transactions)
        }
    }

    override suspend fun getConversionRatesToARS(ids: List<String>): Result<Map<String, KMMDecimal>> {
        return try {
            val idsCoin = ids.joinToString(",")
            val response = apiService.getCoinPrice(coin = idsCoin, vsCurrency = "ars")

            val jsonObject = response.jsonObject
            val resultMap = mutableMapOf<String, KMMDecimal>()

            for ((coin, coinData) in jsonObject) {
                val price = coinData.jsonObject["ars"]?.jsonPrimitive?.contentOrNull?.let {
                    createDecimal(it.toString())
                }
                if (price != null) {
                    resultMap[coin] = price
                }
            }

            Result.success(resultMap)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun updateBalance(balance: Balance) {
        TODO("Not yet implemented")
    }

    override suspend fun getBalanceByCurrency(currency: String): Balance {
        return database.getBalanceDao().getBalanceForCurrency(currency).toDomain()
    }

    override suspend fun getAllTransactions(): List<Transaction> {
        return database.getTransactionDao().getAllTransactions().map { it.toDomain() }
    }
}