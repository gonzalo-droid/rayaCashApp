package com.gondroid.rayacashapp.domain.model.convertRate

import com.gondroid.rayacashapp.KMMDecimal

interface Convertible {
    val value: KMMDecimal
    suspend fun convertTo(target: Convertible, rates: ConversionRateProvider): KMMDecimal
}