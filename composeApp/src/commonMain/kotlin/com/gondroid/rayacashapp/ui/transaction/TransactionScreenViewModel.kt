package com.gondroid.rayacashapp.ui.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gondroid.rayacashapp.domain.Repository
import com.gondroid.rayacashapp.domain.useCases.GetTransactions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TransactionScreenViewModel(
    private val getTransactions: GetTransactions,
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow<TransactionState>(TransactionState())
    val state: StateFlow<TransactionState> = _state

    init {
        viewModelScope.launch {
            val transactions = withContext(Dispatchers.IO) {
                repository.getAllTransactions()
            }
            _state.value = _state.value.copy(transactions = transactions)
        }
    }
}