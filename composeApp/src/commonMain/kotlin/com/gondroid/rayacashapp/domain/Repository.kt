package com.gondroid.rayacashapp.domain

import com.gondroid.rayacashapp.domain.model.Balance

interface Repository {
    suspend fun getBalances(): List<Balance>
    suspend fun insertInitialBalances(): Unit
}