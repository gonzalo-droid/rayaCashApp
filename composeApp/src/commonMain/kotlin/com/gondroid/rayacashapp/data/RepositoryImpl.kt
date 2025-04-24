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
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

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

    override suspend fun getConversionRatesToARS(ids: List<String>): Result<Map<String, Double>>  {
        return try {
            val idsCoin = ids.joinToString(",")
            val response = apiService.getCoinPrice(coin = idsCoin, vsCurrency = "ars")

            val jsonObject = response.jsonObject
            val resultMap = mutableMapOf<String, Double>()

            for ((coin, coinData) in jsonObject) {
                val price = coinData.jsonObject["ars"]?.jsonPrimitive?.doubleOrNull
                if (price != null) {
                    resultMap[coin] = price
                }
            }

            Result.success(resultMap)
        } catch (e: Exception) {
            Result.failure(e)
        }

    }
}