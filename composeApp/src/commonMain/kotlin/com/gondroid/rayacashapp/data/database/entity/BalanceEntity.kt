package com.gondroid.rayacashapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gondroid.rayacashapp.domain.model.balance.Balance
import com.gondroid.rayacashapp.domain.model.convertRate.getCurrency

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
}
