package com.gondroid.rayacashapp.domain.useCases

import com.gondroid.rayacashapp.KMMDecimal
import com.gondroid.rayacashapp.createDecimal
import com.gondroid.rayacashapp.domain.Repository
import com.gondroid.rayacashapp.domain.model.Balance
import com.gondroid.rayacashapp.domain.model.BalanceAmountToARS
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType
import com.gondroid.rayacashapp.plus
import com.gondroid.rayacashapp.roundToDecimal
import com.gondroid.rayacashapp.times
import com.gondroid.rayacashapp.toPlainString

class GetTotalBalance(private val repository: Repository) {

    suspend operator fun invoke(): Result<BalanceAmountToARS> {
        return try {
            val balances: List<Balance> = repository.getBalances()
            val result = repository.getConversionRatesToARS()

            if (result.isSuccess) {
                val conversionRates = result.getOrNull().orEmpty()

                var totalInARS: KMMDecimal = createDecimal("0")
                balances.forEach { balance ->
                    totalInARS =
                        totalInARS.plus(getConversionRates(balance, conversionRates))
                }

                val balanceAmountToARS = BalanceAmountToARS(
                    totalBalance = "ARS ${totalInARS.roundToDecimal().toPlainString()}",
                    balances = balances.map { balance ->
                        balance.amountToARS =
                            "ARS " + getConversionRates(
                                balance,
                                conversionRates
                            ).roundToDecimal().toPlainString()
                        balance
                    }
                )

                Result.success(balanceAmountToARS)
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

    fun getConversionRates(
        balance: Balance,
        conversionRates: Map<CurrencyType, KMMDecimal>
    ): KMMDecimal {
        val amount = createDecimal(balance.amount)
        return when (balance.currency) {
            CurrencyType.ARS -> amount
            CurrencyType.USD -> amount.times(
                conversionRates[CurrencyType.USD] ?: createDecimal("0")
            )

            CurrencyType.BTC -> amount.times(
                conversionRates[CurrencyType.BTC] ?: createDecimal("0")
            )

            CurrencyType.ETH -> amount.times(
                conversionRates[CurrencyType.ETH] ?: createDecimal("0")
            )
        }
    }

}