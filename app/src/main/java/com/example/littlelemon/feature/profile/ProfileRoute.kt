package com.example.littlelemon.feature.profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ProfileRoute(
    onNavigateToOnboarding: () -> Unit,
    viewModel: ProfileViewModel = viewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.effects.collectLatest { effect ->
            when (effect) {
                ProfileEffect.NavigateToOnboarding -> onNavigateToOnboarding()
            }
        }
    }

    ProfileScreen(
        onLogoutClick = viewModel::onLogoutClick,
    )
}
