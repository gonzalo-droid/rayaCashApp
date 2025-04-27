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

    private suspend fun initializeData() {
        try {
            withContext(Dispatchers.IO) {
                repository.insertInitialData()
            }
        } catch (e: Exception) {
            println("Error initializing data}")
        }
    }

     fun loadBalances() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            withContext(Dispatchers.IO) {
                try {
                    val result = withContext(Dispatchers.IO) { getTotalBalance() }
                    result.onSuccess { data ->
                        _state.update {
                            it.copy(
                                totalBalance = data.totalBalance,
                                balances = data.balances,
                                isLoading = false
                            )
                        }
                    }.onFailure { error ->
                        println("Excepción durante la conversión: ${error.message}")
                        _state.update { it.copy(isLoading = true) }
                    }
                } catch (e: Exception) {
                    println("Excepción durante la conversión: ${e.message}")
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }
    }
}