package com.gondroid.rayacashapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gondroid.rayacashapp.domain.model.Balance
import com.gondroid.rayacashapp.domain.model.Currency

@Entity(tableName = "balances")
data class BalanceEntity(
    @PrimaryKey val currency: String,
    val amount: String,
    val updatedAt: String
) {
    fun toDomain(): Balance {
        return Balance(
            currency = getCurrency(currency),
            amount = amount,
            updatedAt = updatedAt
        )
    }

    fun getCurrency(currency: String): Currency {
        return when (currency) {
            Currency.ARS.name -> Currency.ARS
            Currency.USD.name -> Currency.USD
            Currency.BTC.name -> Currency.BTC
            Currency.ETH.name -> Currency.ETH
            else -> {
                Currency.UNKNOWN
            }
        }
    }
}
