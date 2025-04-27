package usecases

import com.gondroid.rayacashapp.domain.model.convertRate.Currency
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType
import com.gondroid.rayacashapp.domain.useCases.GetCurrentRate
import FakeConversionRateProvider
import FakeConversionRatesProvider
import FakeConvertibleFailure
import FakeConvertibleSuccess
import com.gondroid.rayacashapp.shared.createDecimal
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GetCurrentRateTest {

    private val getCurrentRate = GetCurrentRate()

    @Test
    fun `conversion (ARS to Coin) is successful`() = runTest {
        // Given
        val from = Currency(
            type = CurrencyType.ARS,
            value = createDecimal("500")
        )
        val to = Currency(
            type = CurrencyType.USD,
            value = createDecimal("0")
        )
        val rateArsToUsd = createDecimal("1000")
        val provider = FakeConversionRateProvider(rateArsToUsd)

        // When
        val result = getCurrentRate(from, to, provider)

        // Then
        assertTrue(result.isSuccess)
        assertEquals("0.50", result.getOrNull())
    }

    @Test
    fun `conversion (Any to Coin) is successful`() = runTest {
        // Given
        val from = Currency(
            type = CurrencyType.USD,
            value = createDecimal("500")
        )
        val to = Currency(
            type = CurrencyType.ARS,
            value = createDecimal("0")
        )
        val rateArsToUsd = createDecimal("1000")
        val provider = FakeConversionRateProvider(rateArsToUsd)

        // When
        val result = getCurrentRate(from, to, provider)

        // Then
        assertTrue(result.isSuccess)
        assertEquals("500000.00", result.getOrNull())
    }

    @Test
    fun `conversion (Coin to Coin) is successful`() = runTest {
        // Given
        val from = Currency(
            type = CurrencyType.USD,
            value = createDecimal("500")
        )
        val to = Currency(
            type = CurrencyType.BTC,
            value = createDecimal("0")
        )
        val rateUsdToArs = createDecimal("1165.86")
        val rateBtcToArs = createDecimal("109548884")
        val provider = FakeConversionRatesProvider(
            rates = mapOf(
                CurrencyType.USD to rateUsdToArs,
                CurrencyType.BTC to rateBtcToArs
            )
        )

        // When
        val result = getCurrentRate(from, to, provider)

        // Then
        assertTrue(result.isSuccess)
        assertEquals("0.0053211861", result.getOrNull())
    }

    @Test
    fun `conversion (Coin to Coin) is failure`() = runTest {
        // Given
        val from = FakeConvertibleFailure(createDecimal("500"))
        val to = FakeConvertibleSuccess(createDecimal("0"))
        val provider = FakeConversionRateProvider(createDecimal("1000"))

        // When
        val result = getCurrentRate(from, to, provider)

        // Then
        assertTrue(result.isFailure)
    }
}
