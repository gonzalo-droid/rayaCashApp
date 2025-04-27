package com.gondroid.rayacashapp.domain.model

import com.gondroid.rayacashapp.data.database.entity.BalanceEntity
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Balance(
    val currency: CurrencyType = CurrencyType.ARS,
    val amount: String = "0.0",
    var amountToARS: String = "",
    val updatedAt: String = getCurrentLocalTime()
) {
    fun toEntity(): BalanceEntity {
        return BalanceEntity(
            currency = currency.name,
            amount = amount,
            updatedAt = updatedAt
        )
    }
}

