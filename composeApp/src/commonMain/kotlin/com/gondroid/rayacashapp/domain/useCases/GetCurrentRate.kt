package com.gondroid.rayacashapp.domain.useCases

import com.gondroid.rayacashapp.KMMDecimal
import com.gondroid.rayacashapp.createDecimal
import com.gondroid.rayacashapp.div
import com.gondroid.rayacashapp.domain.Repository
import com.gondroid.rayacashapp.domain.model.Balance
import com.gondroid.rayacashapp.domain.model.Currency
import com.gondroid.rayacashapp.domain.model.getIdCurrency
import com.gondroid.rayacashapp.times
import com.gondroid.rayacashapp.toPlainString

class GetCurrentRate() {

    suspend operator fun invoke(
        amount: String,
        fromCurrency: Currency,
        toCurrency: Currency,
        conversionRates: Map<String, KMMDecimal>
    ): Result<String> {
        return try {

            var amountKMMDecimal: KMMDecimal = createDecimal(amount.toString())

            val arsToUsd = conversionRates["usd"] ?: createDecimal("1168.06")
            val arsToBtc = conversionRates["bitcoin"] ?: createDecimal("111209212")
            val arsToEth = conversionRates["ethereum"] ?: createDecimal("2106733")

            val fromRate = when (fromCurrency) {
                Currency.ARS -> createDecimal("1")
                Currency.USD -> arsToUsd
                Currency.BTC -> arsToBtc
                Currency.ETH -> arsToEth
            }

            val toRate = when (toCurrency) {
                Currency.ARS -> createDecimal("1")
                Currency.USD -> arsToUsd
                Currency.BTC -> arsToBtc
                Currency.ETH -> arsToEth
            }

            var total: KMMDecimal

            // from ARS to != ARS
            if(fromCurrency == Currency.ARS && toCurrency != Currency.ARS){
                total = amountKMMDecimal.div(toRate)
            } else if(fromCurrency != Currency.ARS && toCurrency == Currency.ARS){
                total = amountKMMDecimal.times(fromRate)
            } else {
                total = amountKMMDecimal.times(fromRate).div(toRate)
            }

            Result.success(total.toPlainString())
        } catch (e: Exception) {
            println("Exception durante la conversión: ${e.message}")
            Result.failure(e)
        }
    }
}