package com.example.littlelemon.feature.home

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun HomeRoute(
    onNavigateToProfile: () -> Unit,
    viewModel: HomeViewModel = viewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState.value,
        onNavigateToProfile = onNavigateToProfile,
        onSearchPhraseChanged = viewModel::onSearchPhraseChanged,
        onCategorySelected = viewModel::onCategorySelected,
    )
}
