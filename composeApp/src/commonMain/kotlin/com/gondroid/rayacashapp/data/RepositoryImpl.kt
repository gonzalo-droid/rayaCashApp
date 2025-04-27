package com.gondroid.rayacashapp.data

import com.gondroid.rayacashapp.data.database.RayaCashDatabase
import com.gondroid.rayacashapp.data.database.fakeBalanceEntities
import com.gondroid.rayacashapp.data.database.fakeTransactionEntities
import com.gondroid.rayacashapp.data.remote.ApiService
import com.gondroid.rayacashapp.domain.Repository
import com.gondroid.rayacashapp.domain.model.Balance
import com.gondroid.rayacashapp.domain.model.Transaction
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType
import com.gondroid.rayacashapp.domain.model.convertRate.getCurrencyTypeFromString
import com.gondroid.rayacashapp.shared.KMMDecimal
import com.gondroid.rayacashapp.shared.createDecimal
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
            database.getBalanceDao().insertBalances(fakeBalanceEntities)
            database.getTransactionDao().insertTransactions(fakeTransactionEntities)
        }
    }

    override suspend fun getConversionRatesToARS(): Result<Map<CurrencyType, KMMDecimal>> {
        return try {
            val idsCoin = listOf("usd", "bitcoin", "ethereum").joinToString(",")
            val response = apiService.getCoinPrice(coin = idsCoin, vsCurrency = "ars")

            val jsonObject = response.jsonObject
            val resultMap = mutableMapOf<CurrencyType, KMMDecimal>()

            for ((coin, coinData) in jsonObject) {
                val price = coinData.jsonObject["ars"]?.jsonPrimitive?.contentOrNull?.let {
                    createDecimal(it.toString())
                }

                val currencyType = getCurrencyTypeFromString(coin)
                if (price != null && currencyType != null) {
                    resultMap[currencyType] = price
                }
            }

            Result.success(resultMap)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }

    override suspend fun updateBalances(balances: List<Balance>): Boolean {
        return try {
            database.getBalanceDao().upsertBalances(balances.map { it.toEntity() })
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getBalanceByCurrency(currency: String): Balance {
        return database.getBalanceDao().getBalanceForCurrency(currency).toDomain()
    }

    override suspend fun getAllTransactions(): List<Transaction> {
        return database.getTransactionDao().getAllTransactions().map { it.toDomain() }
    }

    override suspend fun saveTransaction(transaction: Transaction): Boolean {
        return try {
            database.getTransactionDao().insertTransaction(transaction.toEntity())
            true
        } catch (e: Exception) {
            false
        }
    }

    override suspend fun getBalanceByCurrencies(currencies: List<String>): List<Balance> {
        return database.getBalanceDao().getBalancesForCurrencies(currencies).map { it.toDomain() }
    }
}