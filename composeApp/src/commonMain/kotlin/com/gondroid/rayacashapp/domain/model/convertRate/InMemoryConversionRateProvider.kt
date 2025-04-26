package com.gondroid.rayacashapp.domain.model.convertRate

import com.gondroid.rayacashapp.shared.KMMDecimal
import com.gondroid.rayacashapp.shared.createDecimal

class InMemoryConversionRateProvider(
    private val rates: Map<CurrencyType, KMMDecimal>
) : ConversionRateProvider {

    override fun getRate(type: CurrencyType): KMMDecimal {
        return rates[type] ?: createDecimal("1")
    }
}
