package com.example.s8133896assignment2.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8133896assignment2.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface LoginUiState {
    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data class Success(val keypass: String) : LoginUiState
    data class Error(val message: String) : LoginUiState
}

class LoginViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(studentId: String, firstName: String) {
        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            try {
                val response = repository.login(studentId, firstName)

                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = LoginUiState.Success(
                        keypass = response.body()!!.keypass
                    )
                } else {
                    _uiState.value = LoginUiState.Error(
                        message = "Login failed. Check your student ID and first name."
                    )
                }
            } catch (exception: Exception) {
                _uiState.value = LoginUiState.Error(
                    message = "Network error. Check your internet connection and try again."
                )
            }
        }
    }
}