package com.example.s8133896assignment2.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.s8133896assignment2.data.model.DashboardResponse
import com.example.s8133896assignment2.data.repository.DashboardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * UI states used by the Fitness Dashboard screen.
 */
sealed interface DashboardUiState {
    data object Loading : DashboardUiState
    data class Success(val dashboard: DashboardResponse) : DashboardUiState
    data class Error(val message: String) : DashboardUiState
}

/**
 * Loads Fitness dashboard entities through the injected repository.
 */
class DashboardViewModel(
    private val repository: DashboardRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(
        DashboardUiState.Loading
    )

    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    fun loadDashboard(keypass: String) {
        viewModelScope.launch {
            _uiState.value = DashboardUiState.Loading

            try {
                val response = repository.getDashboard(keypass)

                if (response.isSuccessful && response.body() != null) {
                    _uiState.value = DashboardUiState.Success(
                        dashboard = response.body()!!
                    )
                } else {
                    _uiState.value = DashboardUiState.Error(
                        message = "Unable to load dashboard. HTTP code: ${response.code()}"
                    )
                }
            } catch (exception: Exception) {
                _uiState.value = DashboardUiState.Error(
                    message = "Network error while loading dashboard. Please try again."
                )
            }
        }
    }
}