package com.gondroid.rayacashapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gondroid.rayacashapp.domain.GetTotalBalance
import com.gondroid.rayacashapp.domain.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeScreenViewModel(
    val getTotalBalance: GetTotalBalance,
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow<HomeState>(HomeState())
    val state: StateFlow<HomeState> = _state

    init {
        viewModelScope.launch {
            initializeData()

            getBalances()
        }
    }

    private suspend fun initializeData() = withContext(Dispatchers.IO) {
        try {
            repository.insertInitialBalances()

            val result = getTotalBalance()
            withContext(Dispatchers.Main) {
                result.onSuccess { total ->
                    _state.update { it.copy(totalBalance = total) }
                }.onFailure { error ->
                    // Log.e("HomeViewModel", "Failed to get total balance", error)
                }
            }
        } catch (e: Exception) {
            // Log.e("HomeViewModel", "Initialization failed", e)
        }
    }

    private fun getBalances() {
        viewModelScope.launch {
            val result = withContext(Dispatchers.IO) {
                repository.getBalances()
            }
            _state.update { it.copy(balances = result) }
        }
    }
}