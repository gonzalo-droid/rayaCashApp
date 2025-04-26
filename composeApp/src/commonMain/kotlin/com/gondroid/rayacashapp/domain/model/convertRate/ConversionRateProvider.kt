package com.gondroid.rayacashapp.domain.model.convertRate

import com.gondroid.rayacashapp.KMMDecimal

interface ConversionRateProvider {
    fun getRate(type: CurrencyType): KMMDecimal
}