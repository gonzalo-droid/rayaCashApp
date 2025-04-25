package com.gondroid.rayacashapp.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gondroid.rayacashapp.domain.useCases.GetTotalBalance
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
        }
    }

    private suspend fun initializeData() = withContext(Dispatchers.IO) {
        try {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            repository.insertInitialBalances()

            val result = getTotalBalance()
            withContext(Dispatchers.Main) {
                result.onSuccess { data ->
                    _state.update {
                        it.copy(
                            totalBalance = data.totalBalance,
                            balances = data.balances,
                            isLoading = false
                        )
                    }
                }.onFailure { error ->
                    // Log.e("HomeViewModel", "Failed to get total balance", error)
                }
            }
        } catch (e: Exception) {
            println("Excepción durante la conversión: ${e.message}")
        }
    }
}