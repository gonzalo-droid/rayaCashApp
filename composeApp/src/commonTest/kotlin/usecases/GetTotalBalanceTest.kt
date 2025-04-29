package usecases

import com.gondroid.rayacashapp.domain.model.balance.Balance
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType
import com.gondroid.rayacashapp.domain.useCases.GetTotalBalance
import FakeRepository
import com.gondroid.rayacashapp.shared.createDecimal
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class GetTotalBalanceTest {

    private val fakeRepository = FakeRepository()

    @Test
    fun `Given valid balances and conversion rates when getting total balance then it should calculate correctly`() =
        runBlocking {
            // Given
            val balances = mutableListOf(
                Balance(currency = CurrencyType.USD, amount = "1000.00"),
                Balance(currency = CurrencyType.BTC, amount = "500.00")
            )

            val conversionRates = mapOf(
                CurrencyType.USD to createDecimal("100"),
                CurrencyType.BTC to createDecimal("150")
            )

            fakeRepository.balances = balances
            fakeRepository.conversions = conversionRates

            val getTotalBalance = GetTotalBalance(fakeRepository)

            // When
            val result = getTotalBalance()

            // Then
            assertTrue(result.isSuccess)

            val data = result.getOrNull()

            assertNotNull(data)

            assertEquals("ARS 175000.00", data?.totalBalance)

            assertEquals(
                "ARS 100000.00",
                data?.balances?.first { it.currency == CurrencyType.USD }?.amountToARS
            )
            assertEquals(
                "ARS 75000.00",
                data?.balances?.first { it.currency == CurrencyType.BTC }?.amountToARS
            )
        }

}