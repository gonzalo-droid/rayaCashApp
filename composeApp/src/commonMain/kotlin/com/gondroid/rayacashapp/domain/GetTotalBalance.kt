package com.gondroid.rayacashapp.domain

import com.gondroid.rayacashapp.domain.model.Balance


class GetTotalBalance(private val repository: Repository) {

    suspend operator fun invoke(): String {

        val balances : List<Balance> = repository.getBalances()
        // sum amount to convert ARS
        val totalBalance = balances.sumOf { it.amount }

        return ""
    }
}