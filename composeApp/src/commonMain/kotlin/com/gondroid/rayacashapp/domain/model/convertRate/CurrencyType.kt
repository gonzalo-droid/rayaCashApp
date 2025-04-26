package com.gondroid.rayacashapp.domain.model.convertRate

import com.gondroid.rayacashapp.KMMDecimal
import com.gondroid.rayacashapp.div
import com.gondroid.rayacashapp.times

enum class CurrencyType {
    ARS, USD, BTC, ETH
}

data class Currency(
    val type: CurrencyType,
    override val value: KMMDecimal
) : Convertible {

    override suspend fun convertTo(target: Convertible, rates: ConversionRateProvider): KMMDecimal {
        require(target is Currency)

        val fromRate = rates.getRate(type)
        val toRate = rates.getRate(target.type)

        return when {
            type == CurrencyType.ARS && target.type != CurrencyType.ARS -> value.div(toRate)
            type != CurrencyType.ARS && target.type == CurrencyType.ARS -> value.times(fromRate)
            else -> value.times(fromRate).div(toRate)
        }
    }
}