package com.example.littlelemon.feature.home

import com.example.littlelemon.domain.model.MenuItem

data class HomeUiState(
    val menuItems: List<MenuItem> = emptyList(),
    val searchPhrase: String = "",
    val isSyncing: Boolean = false,
    val errorMessage: String? = null,
)
