package com.example.s8133896assignment2.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8133896assignment2.data.repository.AuthRepositoryInterface
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

/**
 * Holds the state and validation logic for the Login screen.
 */
class LoginViewModel(
    private val repository: AuthRepositoryInterface
) : ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Idle)
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    /**
     * Validates login input before making the authentication API request.
     */
    fun login(studentId: String, firstName: String) {
        val trimmedStudentId = studentId.trim()
        val trimmedFirstName = firstName.trim()

        if (trimmedStudentId.isEmpty()) {
            _uiState.value = LoginUiState.Error(
                message = "Please enter your student ID."
            )
            return
        }

        if (trimmedFirstName.isEmpty()) {
            _uiState.value = LoginUiState.Error(
                message = "Please enter your first name."
            )
            return
        }

        viewModelScope.launch {
            _uiState.value = LoginUiState.Loading

            try {
                val response = repository.login(
                    studentId = trimmedStudentId,
                    firstName = trimmedFirstName
                )

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