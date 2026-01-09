package com.example.littlelemon.feature.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.di.AppContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val menuRepository = AppContainer.get(application).menuRepository

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            menuRepository.observeMenuItems().collect { items ->
                _uiState.update { it.copy(menuItems = items) }
            }
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isSyncing = true, errorMessage = null) }

            val result =
                withContext(Dispatchers.IO) {
                    menuRepository.syncMenu()
                }

            _uiState.update {
                it.copy(
                    isSyncing = false,
                    errorMessage = result.exceptionOrNull()?.message,
                )
            }
        }
    }

    fun onSearchPhraseChanged(value: String) {
        _uiState.update { it.copy(searchPhrase = value) }
    }
}
