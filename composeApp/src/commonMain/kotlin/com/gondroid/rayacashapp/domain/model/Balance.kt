package com.gondroid.rayacashapp.domain.model

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
)

private fun getCurrentLocalTime(): String {
    val instant: Instant = Clock.System.now()
    val localTime: LocalDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return localTime.toString()
}
