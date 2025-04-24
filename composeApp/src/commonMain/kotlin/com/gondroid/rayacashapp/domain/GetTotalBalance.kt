package com.gondroid.rayacashapp.domain

import com.gondroid.rayacashapp.domain.model.Balance
import com.gondroid.rayacashapp.domain.model.Currency


class GetTotalBalance(private val repository: Repository) {

    suspend operator fun invoke(): Result<String> {
        return try {
            val balances: List<Balance> = repository.getBalances()

            val result = repository.getConversionRatesToARS(listOf("usd", "bitcoin", "ethereum"))

            if (result.isSuccess) {
                val conversionRates = result.getOrNull().orEmpty()

                val totalInARS = balances.sumOf { balance ->
                    when (balance.currency) {
                        Currency.ARS -> balance.amount
                        Currency.USD -> balance.amount * (conversionRates["usd"] ?: 0.0)
                        Currency.BTC -> balance.amount * (conversionRates["bitcoin"] ?: 0.0)
                        Currency.ETH -> balance.amount * (conversionRates["ethereum"] ?: 0.0)
                        else -> 0.0
                    }
                }

                Result.success("ARS: $totalInARS")
            } else {
                val error = result.exceptionOrNull()
                println("Error al obtener tasas de conversión: ${error?.message}")
                Result.failure(error ?: Exception("Error desconocido"))
            }
        } catch (e: Exception) {
            println("Excepción durante la conversión: ${e.message}")
            Result.failure(e)
        }
    }
}