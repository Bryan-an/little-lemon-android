package com.example.littlelemon.feature.onboarding

import androidx.compose.runtime.Composable
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun OnboardingRoute(
    viewModel: OnboardingViewModel = viewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    OnboardingScreen(
        uiState = uiState.value,
        actions =
        OnboardingActions(
            onFirstNameChanged = viewModel::onFirstNameChanged,
            onLastNameChanged = viewModel::onLastNameChanged,
            onEmailChanged = viewModel::onEmailChanged,
            onRegisterClick = viewModel::onRegisterClick,
        ),
    )
}
