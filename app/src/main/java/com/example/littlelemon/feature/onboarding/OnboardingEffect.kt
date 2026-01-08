package com.example.littlelemon.feature.onboarding

import androidx.annotation.StringRes

sealed interface OnboardingEffect {
    data class ShowMessage(
        @StringRes val messageRes: Int,
    ) : OnboardingEffect

    data object NavigateToHome : OnboardingEffect
}
