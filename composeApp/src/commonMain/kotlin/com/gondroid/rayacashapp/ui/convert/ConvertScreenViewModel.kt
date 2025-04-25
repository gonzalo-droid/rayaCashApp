package com.gondroid.rayacashapp.ui.convert

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gondroid.rayacashapp.domain.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ConvertScreenViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _state = MutableStateFlow<ConvertState>(ConvertState())
    val state: StateFlow<ConvertState> = _state

    init {
        viewModelScope.launch {

        }
    }
}