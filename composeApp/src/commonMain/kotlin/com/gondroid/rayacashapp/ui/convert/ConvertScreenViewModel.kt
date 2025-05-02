package com.gondroid.rayacashapp.ui.convert

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gondroid.rayacashapp.domain.Repository
import com.gondroid.rayacashapp.domain.model.Transaction
import com.gondroid.rayacashapp.domain.model.balance.DefaultBalanceUpdater
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

    private val canConvert = snapshotFlow { _state.value.fromCoinField.text }

    init {
        _state.value = _state.value.copy(isLoading = true)
        getRates()

        canConvert
            .onEach {
                _state.value = _state.value.copy(canConvert = it.isNotBlank())
            }
            .launchIn(viewModelScope)

        _state.value = _state.value.copy(
            fromCoinSelected = currencyList[0],
            toCoinSelected = currencyList[1],
        )
        getBalanceByCurrency()
    }

    fun saveCurrencyFrom(currency: CurrencyType, amount: String) {
        _state.value = _state.value.copy(fromCoinSelected = currency)
        if (currency == _state.value.toCoinSelected) {
            val toCurrency = currencyList.first { it != currency }
            saveCurrencyTo(toCurrency, _state.value.fromCoinField.text.toString())
        } else {
            convertAmount(amount)
        }
        getBalanceByCurrency()
    }

    fun saveCurrencyTo(currency: CurrencyType, amount: String) {
        _state.value = _state.value.copy(toCoinSelected = currency)
        convertAmount(amount)
    }

    fun filterCurrencies(isCoinFrom: Boolean) {
        val filteredCoins = if (isCoinFrom) {
            currencyList
        } else {
            currencyList.filter { it != _state.value.fromCoinSelected }
        }

        _state.value = _state.value.copy(
            coins = filteredCoins
        )
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
                updateBalance(transaction, DefaultBalanceUpdater())
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

    fun setMaxValue() {
        _state.value.fromCoinField.edit {
            replace(0, _state.value.fromCoinField.text.length, _state.value.balance.amount)
        }
    }
}
