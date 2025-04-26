package com.gondroid.rayacashapp.ui.convert

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gondroid.rayacashapp.domain.Repository
import com.gondroid.rayacashapp.domain.model.Coin
import com.gondroid.rayacashapp.domain.model.coinsList
import com.gondroid.rayacashapp.domain.useCases.GetCurrentRate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConvertScreenViewModel(
    private val getCurrentRate: GetCurrentRate,
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow<ConvertState>(ConvertState(conversionRates = emptyMap()))
    val state: StateFlow<ConvertState> = _state


    private val canConvert = snapshotFlow { state.value.fromCoinField.text.toString() }

    init {
        getRates()

        canConvert
            .onEach {
                _state.value = _state.value.copy(canConvert = it.isNotEmpty())
            }.launchIn(viewModelScope)

        _state.value = _state.value.copy(
            fromCoinSelected = coinsList[0],
            toCoinSelected = coinsList[1],
        )
        getBalanceByCurrency()
    }

    fun saveCoinFrom(coin: Coin) {
        _state.value = _state.value.copy(fromCoinSelected = coin)
        getBalanceByCurrency()
    }

    fun saveCoinTo(coin: Coin) {
        _state.value = _state.value.copy(toCoinSelected = coin)
    }

    fun filterCoins(isCoinFrom: Boolean) {
        _state.value = _state.value.copy(
            coins = coinsList.filter {
                it.currency != if (isCoinFrom) {
                    _state.value.toCoinSelected.currency
                } else {
                    _state.value.fromCoinSelected.currency
                }
            }
        )
    }

    fun getBalanceByCurrency() {
        viewModelScope.launch {
            val balance = withContext(Dispatchers.IO) {
                repository.getBalanceByCurrency(state.value.fromCoinSelected.currency.name)
            }
            _state.value = _state.value.copy(balance = balance)
        }
    }

    fun getRates() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getConversionRatesToARS(listOf("usd", "bitcoin", "ethereum"))
            }
            result.onSuccess {
                _state.value = _state.value.copy(conversionRates = it)
            }.onFailure {
                _state.value = _state.value.copy(conversionRates = emptyMap())
            }
        }
    }

    fun convertAmount(amount: String) {
        if (amount.isBlank()) {
            _state.value = _state.value.copy(amountConverted = "0")
            return
        }

        if (
            _state.value.conversionRates.isNotEmpty()
        ) {
            viewModelScope.launch {
                val result = withContext(Dispatchers.IO) {
                    getCurrentRate(
                        amount = amount,
                        fromCurrency = _state.value.fromCoinSelected.currency,
                        toCurrency = _state.value.toCoinSelected.currency,
                        conversionRates = _state.value.conversionRates
                    )
                }
                result.onSuccess {
                    _state.value = _state.value.copy(amountConverted = it)
                }.onFailure {
                    _state.value = _state.value.copy(amountConverted = "0")
                }
            }
        }

    }
}