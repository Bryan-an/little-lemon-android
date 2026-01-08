package com.example.littlelemon.feature.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collect

@Composable
fun OnboardingRoute(
    onShowMessage: (Int) -> Unit,
    onNavigateToHome: () -> Unit,
    viewModel: OnboardingViewModel = viewModel(),
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effects.collect { effect ->
            when (effect) {
                is OnboardingEffect.ShowMessage -> {
                    onShowMessage(effect.messageRes)
                }

                OnboardingEffect.NavigateToHome -> onNavigateToHome()
            }
        }
    }

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
