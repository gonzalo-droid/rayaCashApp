package com.gondroid.rayacashapp.ui.convert

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gondroid.rayacashapp.domain.Repository
import com.gondroid.rayacashapp.domain.model.Transaction
import com.gondroid.rayacashapp.domain.model.convertRate.Currency
import com.gondroid.rayacashapp.domain.model.convertRate.CurrencyType
import com.gondroid.rayacashapp.domain.model.convertRate.InMemoryConversionRateProvider
import com.gondroid.rayacashapp.domain.model.convertRate.currencyList
import com.gondroid.rayacashapp.domain.useCases.GetCurrentRate
import com.gondroid.rayacashapp.domain.useCases.UpdateBalance
import com.gondroid.rayacashapp.shared.createDecimal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConvertScreenViewModel(
    private val updateBalance: UpdateBalance,
    private val getCurrentRate: GetCurrentRate,
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow<ConvertState>(ConvertState(conversionRates = emptyMap()))
    val state: StateFlow<ConvertState> = _state

    private var eventChannel = Channel<ConvertEvent>()
    val event = eventChannel.receiveAsFlow()

    private val canConvert = snapshotFlow { state.value.fromCoinField.text.toString() }

    init {
        _state.value = _state.value.copy(isLoading = true)
        getRates()
        canConvert
            .onEach {
                _state.value = _state.value.copy(canConvert = it.isNotEmpty())
            }.launchIn(viewModelScope)

        _state.value = _state.value.copy(
            fromCoinSelected = currencyList[0],
            toCoinSelected = currencyList[1],
        )
        getBalanceByCurrency()
    }

    fun saveCurrencyFrom(currency: CurrencyType) {
        _state.value = _state.value.copy(fromCoinSelected = currency)
        getBalanceByCurrency()
    }

    fun saveCurrencyTo(currency: CurrencyType) {
        _state.value = _state.value.copy(toCoinSelected = currency)
    }

    fun filterCurrencies(isCoinFrom: Boolean) {
        _state.value = _state.value.copy(
            coins = currencyList.filter {
                it != if (isCoinFrom) {
                    _state.value.toCoinSelected
                } else {
                    _state.value.fromCoinSelected
                }
            }
        )
    }

    fun getBalanceByCurrency() {
        viewModelScope.launch {
            val balance = withContext(Dispatchers.IO) {
                repository.getBalanceByCurrency(state.value.fromCoinSelected.name)
            }
            _state.value = _state.value.copy(balance = balance)
        }
    }

    fun getRates() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getConversionRatesToARS()
            }
            result.onSuccess {
                _state.value = _state.value.copy(conversionRates = it, isLoading = false)
            }.onFailure {
                _state.value = _state.value.copy(conversionRates = emptyMap(), isLoading = false)
            }
        }
    }

    fun convertAmount(amount: String) {
        if (
            _state.value.conversionRates.isNotEmpty() && amount.isNotBlank()
        ) {
            viewModelScope.launch {
                val result = withContext(Dispatchers.IO) {
                    try {
                        val from = Currency(
                            type = _state.value.fromCoinSelected,
                            value = createDecimal(amount)
                        )
                        val to = Currency(
                            type = _state.value.toCoinSelected,
                            value = createDecimal("0")
                        )

                        val rateProvider =
                            InMemoryConversionRateProvider(_state.value.conversionRates)

                        getCurrentRate(from = from, to = to, rateProvider = rateProvider)
                    } catch (e: Exception) {
                        Result.failure(e)
                    }
                }
                result.onSuccess {
                    _state.value = _state.value.copy(amountConverted = it)
                }.onFailure {
                    _state.value = _state.value.copy(amountConverted = "0")
                }
            }
        }

    }

    fun saveTransaction() {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val transaction = Transaction(
                fromCurrency = _state.value.fromCoinSelected,
                fromAmount = _state.value.fromCoinField.text.toString(),
                toCurrency = _state.value.toCoinSelected,
                toAmount = _state.value.amountConverted,
            )
            val result = withContext(Dispatchers.IO) {
                updateBalance(transaction)
            }

            result.onSuccess {
                _state.value = _state.value.copy(isLoading = false)
                eventChannel.send(ConvertEvent.Success)
            }.onFailure {
                _state.value = _state.value.copy(isLoading = false)
                eventChannel.send(ConvertEvent.Fail("Ocurrio un error al guardar la transacción"))
            }

        }
    }
}