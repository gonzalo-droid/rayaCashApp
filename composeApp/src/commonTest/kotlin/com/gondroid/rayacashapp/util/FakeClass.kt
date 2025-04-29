package com.gondroid.rayacashapp.util

import com.gondroid.rayacashapp.domain.model.convertRate.ConversionRateProvider
import com.gondroid.rayacashapp.domain.model.convertRate.Convertible
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType
import com.gondroid.rayacashapp.shared.KMMDecimal
import com.gondroid.rayacashapp.shared.createDecimal

class FakeConvertibleSuccess(
    override val value: KMMDecimal
) : Convertible {

    override suspend fun convertTo(
        target: Convertible,
        rates: ConversionRateProvider
    ): KMMDecimal {
        return value
    }
}

class FakeConvertibleFailure(
    override val value: KMMDecimal
) : Convertible {

    override suspend fun convertTo(
        target: Convertible,
        rates: ConversionRateProvider
    ): KMMDecimal {
        throw IllegalArgumentException("Conversion failed")
    }
}

class FakeConversionRatesProvider(
private val rates: Map<CurrencyType, KMMDecimal>,
) : ConversionRateProvider {

    override fun getRate(type: CurrencyType): KMMDecimal {
        return rates[type] ?: createDecimal("1")
    }
}

class FakeConversionRateProvider(
    private val rates: KMMDecimal,
) : ConversionRateProvider {

    override fun getRate(type: CurrencyType): KMMDecimal {
        return rates
    }
}


