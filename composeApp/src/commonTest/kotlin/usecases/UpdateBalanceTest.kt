import com.gondroid.rayacashapp.domain.model.balance.Balance
import com.gondroid.rayacashapp.domain.model.Transaction
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType.ARS
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType.USD
import com.gondroid.rayacashapp.domain.useCases.UpdateBalance
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UpdateBalanceTest {

    private val fakeRepository = FakeRepository()

    @Test
    fun `Given a successful transaction when updating balance then balances should be updated correctly`() {
        // Given
        val initAmountFrom = "1000.00"
        val initAmountTo = "1000.00"

        val amountFrom = "100.00"
        val amountTo = "5000.00"

        val initialBalanceFrom = Balance(currency = USD, amount = initAmountFrom)
        val initialBalanceTo = Balance(currency = ARS, amount = initAmountTo)

        fakeRepository.balances = mutableListOf(initialBalanceFrom, initialBalanceTo)

        val transaction = Transaction(
            fromCurrency = USD,
            fromAmount = amountFrom,
            toCurrency = ARS,
            toAmount = amountTo
        )

        val updateBalance = UpdateBalance(fakeRepository)

        // When
        val result = runBlocking {
            updateBalance(transaction, DefaultBalanceUpdater())
        }

        // Then
        assertTrue(result.isSuccess)
    }

    @Test
    fun `Given a failed transaction save when updating balance then should return failure with error message`() {
        // Given
        val initialBalanceFrom = Balance(currency = USD, amount = "1000")
        val initialBalanceTo = Balance(currency = ARS, amount = "50000")

        fakeRepository.balances = mutableListOf(initialBalanceFrom, initialBalanceTo)
        fakeRepository.saveTransactionResult = false

        val transaction = Transaction(
            fromCurrency = USD,
            toCurrency = ARS,
            fromAmount = "100",
            toAmount = "5000"
        )

        val updateBalance = UpdateBalance(fakeRepository)

        // When
        val result = runBlocking {
            updateBalance(transaction, DefaultBalanceUpdater())
        }

        // Then
        assertTrue(result.isFailure)
        assertEquals("Error al guardar la transacción", result.exceptionOrNull()?.message)
    }
}
