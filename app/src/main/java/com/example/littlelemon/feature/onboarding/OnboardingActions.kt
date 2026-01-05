package com.example.littlelemon.feature.onboarding

data class OnboardingActions(
    val onFirstNameChanged: (String) -> Unit,
    val onLastNameChanged: (String) -> Unit,
    val onEmailChanged: (String) -> Unit,
    val onRegisterClick: () -> Unit,
)
