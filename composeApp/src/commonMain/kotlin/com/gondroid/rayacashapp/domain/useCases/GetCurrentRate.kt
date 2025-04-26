package com.gondroid.rayacashapp.domain.useCases

import com.gondroid.rayacashapp.domain.model.convertRate.ConversionRateProvider
import com.gondroid.rayacashapp.domain.model.convertRate.Convertible
import com.gondroid.rayacashapp.shared.toPlainString

class GetCurrentRate() {

    suspend operator fun invoke(
        from: Convertible,
        to: Convertible,
        rateProvider: ConversionRateProvider
    ): Result<String> {
        return try {
            val result = from.convertTo(to, rateProvider)
            Result.success(result.toPlainString())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}