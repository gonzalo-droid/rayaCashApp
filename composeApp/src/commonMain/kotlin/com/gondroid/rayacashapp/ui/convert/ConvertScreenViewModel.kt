package com.gondroid.rayacashapp.ui.convert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gondroid.rayacashapp.domain.Repository
import com.gondroid.rayacashapp.domain.model.Coin
import com.gondroid.rayacashapp.domain.model.coinsList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ConvertScreenViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow<ConvertState>(ConvertState())
    val state: StateFlow<ConvertState> = _state

    init {
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
}