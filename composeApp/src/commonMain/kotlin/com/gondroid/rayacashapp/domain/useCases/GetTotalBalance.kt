package com.gondroid.rayacashapp.domain.useCases

import com.gondroid.rayacashapp.domain.Repository
import com.gondroid.rayacashapp.domain.model.Balance
import com.gondroid.rayacashapp.domain.model.BalanceAmountToARS
import com.gondroid.rayacashapp.domain.model.convertRate.Convertible
import com.gondroid.rayacashapp.domain.model.convertRate.Currency
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType
import com.gondroid.rayacashapp.domain.model.convertRate.InMemoryConversionRateProvider
import com.gondroid.rayacashapp.shared.KMMDecimal
import com.gondroid.rayacashapp.shared.createDecimal
import com.gondroid.rayacashapp.shared.plus
import com.gondroid.rayacashapp.shared.roundToDecimal
import com.gondroid.rayacashapp.shared.toPlainString

class GetTotalBalance(
    private val repository: Repository
) {

    suspend operator fun invoke(): Result<BalanceAmountToARS> {
        return try {

            val balances: List<Balance> = repository.getBalances()

            val result = repository.getConversionRatesToARS()

            if (result.isSuccess) {
                val conversionRates = result.getOrNull().orEmpty()

                val currenciesFrom: List<Convertible> = balances.map { balance ->
                    Currency(
                        type = balance.currency,
                        value = createDecimal(balance.amount)
                    )
                }

                val argConvert = Currency(
                    type = CurrencyType.ARS,
                    value = createDecimal("0")
                )

                val rateProvider = InMemoryConversionRateProvider(conversionRates)

                var totalInARS: KMMDecimal = createDecimal("0")

                currenciesFrom.forEach { currency ->
                    totalInARS = totalInARS.plus(currency.convertTo(argConvert, rateProvider))
                }
                println("Total en ARS: ${totalInARS.toPlainString()}")
                val updatedBalances = balances.map { balance ->
                    val currency = Currency(
                        type = balance.currency,
                        value = createDecimal(balance.amount)
                    )
                    balance.amountToARS =
                        "ARS " + currency.convertTo(argConvert, rateProvider)
                            .roundToDecimal()
                            .toPlainString()
                    balance
                }

                val data = BalanceAmountToARS(
                    totalBalance = "ARS ${totalInARS.roundToDecimal().toPlainString()}",
                    balances = updatedBalances
                )

                Result.success(data)
            } else {
                val error = result.exceptionOrNull()
                println("Error al obtener los balances: ${error?.message}")
                Result.failure(error ?: Exception("Error desconocido"))
            }
        } catch (e: Exception) {
            println("Excepción durante la conversión: ${e.message}")
            Result.failure(e)
        }
    }
}