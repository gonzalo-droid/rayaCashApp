package com.gondroid.rayacashapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gondroid.rayacashapp.data.database.entity.BalanceEntity

@Dao
interface BalanceDAO {

    @Query("SELECT * FROM balances")
    suspend fun getAllBalances(): List<BalanceEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBalances(balances: List<BalanceEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertBalances(balances: List<BalanceEntity>)

    @Query("UPDATE balances SET amount = :amount WHERE currency = :currency")
    suspend fun updateBalance(currency: String, amount: Double)

    @Query("SELECT * FROM balances WHERE currency = :currency LIMIT 1")
    suspend fun getBalanceForCurrency(currency: String): BalanceEntity

    @Query("SELECT * FROM balances WHERE currency IN (:currencies)")
    suspend fun getBalancesForCurrencies(currencies: List<String>): List<BalanceEntity>

}
