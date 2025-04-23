package com.gondroid.rayacashapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gondroid.rayacashapp.data.database.generateUUID


@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String = generateUUID(),
    val amount: Double,
    val fromCurrency: String,
    val toCurrency: String,
    val date: String,
    val description: String
)
