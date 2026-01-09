package com.example.littlelemon.feature.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.di.AppContainer
import com.example.littlelemon.domain.model.MenuItem
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

    private var allMenuItems: List<MenuItem> = emptyList()

    init {
        viewModelScope.launch {
            menuRepository.observeMenuItems().collect { items ->
                allMenuItems = items
                updateVisibleMenuItems()
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
        updateVisibleMenuItems()
    }

    fun onCategorySelected(category: MenuCategory) {
        _uiState.update { it.copy(selectedCategory = category) }
        updateVisibleMenuItems()
    }

    private fun updateVisibleMenuItems() {
        _uiState.update { state ->
            state.copy(menuItems = filteredMenuItems(state))
        }
    }

    private fun filteredMenuItems(state: HomeUiState): List<MenuItem> {
        val categoryValue = state.selectedCategory.apiValue
        val query = state.searchPhrase.trim()

        return allMenuItems
            .asSequence()
            .let { sequence ->
                if (categoryValue == null) {
                    sequence
                } else {
                    sequence.filter { it.category.equals(categoryValue, ignoreCase = true) }
                }
            }
            .let { sequence ->
                if (query.isBlank()) {
                    sequence
                } else {
                    sequence.filter { item ->
                        item.title.contains(query, ignoreCase = true) ||
                            item.description.contains(query, ignoreCase = true)
                    }
                }
            }
            .toList()
    }
}
