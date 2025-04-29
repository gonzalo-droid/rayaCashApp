package com.gondroid.rayacashapp.domain.model.balance


import com.gondroid.rayacashapp.domain.model.Transaction
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

class DefaultBalanceUpdaterTest {

    private lateinit var updater: DefaultBalanceUpdater

    @BeforeTest
    fun setUp() {
        updater = DefaultBalanceUpdater()
    }

    @Test
    fun `update should correctly update balances based on transaction`() = runTest {
        val balances = listOf(
            Balance(currency = CurrencyType.USD, amount = "400.00"),
            Balance(currency = CurrencyType.BTC, amount = "0.0040030000")
        )

        val transaction = Transaction(
            fromCurrency = CurrencyType.USD,
            fromAmount = "100.00",
            toCurrency = CurrencyType.BTC,
            toAmount = "0.0053"
        )
        val updatedBalances = updater.update(transaction, balances)

        val updatedUSD = updatedBalances.first { it.currency == CurrencyType.USD }
        val updatedEUR = updatedBalances.first { it.currency == CurrencyType.BTC }

        assertEquals("300.00", updatedUSD.amount)
        assertEquals("0.0093030000", updatedEUR.amount)
    }
}
