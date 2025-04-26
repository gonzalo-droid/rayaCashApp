package com.gondroid.rayacashapp.domain.model.convertRate

import com.gondroid.rayacashapp.shared.KMMDecimal

interface ConversionRateProvider {
    fun getRate(type: CurrencyType): KMMDecimal
}