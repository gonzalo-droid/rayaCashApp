package com.gondroid.rayacashapp.domain.model

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Balance(
    val currency: Currency,
    val amount: Double,
    val updatedAt: String = getCurrentDayOfTheYear()
)

private fun getCurrentDayOfTheYear(): String {
    val instant: Instant = Clock.System.now()
    val localTime: LocalDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return localTime.toString()
}
