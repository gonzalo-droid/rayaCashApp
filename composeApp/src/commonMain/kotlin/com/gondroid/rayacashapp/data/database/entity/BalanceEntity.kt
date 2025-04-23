package com.gondroid.rayacashapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "balances")
data class BalanceEntity(
    @PrimaryKey val currency: String, // Ej: "ARS", "USD", "BTC", "ETH"
    val amount: Double
)
