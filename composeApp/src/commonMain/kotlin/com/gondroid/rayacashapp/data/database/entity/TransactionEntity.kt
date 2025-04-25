package com.gondroid.rayacashapp.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.gondroid.rayacashapp.data.database.generateUUID
import com.gondroid.rayacashapp.domain.model.Transaction
import com.gondroid.rayacashapp.domain.model.TransactionStatus
import com.gondroid.rayacashapp.domain.model.getCurrency
import com.gondroid.rayacashapp.formatLocalDateTimeString


@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String = generateUUID(),
    val fromCurrency: String,
    val fromAmount: String,
    val toCurrency: String,
    val toAmount: String,
    val date: String,
    val status: String,
) {
    fun toDomain(): Transaction {
        return Transaction(
            id = id,
            fromCurrency = getCurrency(fromCurrency),
            fromAmount = fromAmount,
            toCurrency = getCurrency(toCurrency),
            toAmount = toAmount,
            date = formatLocalDateTimeString(date),
            status = TransactionStatus.valueOf(status)
        )
    }
}
