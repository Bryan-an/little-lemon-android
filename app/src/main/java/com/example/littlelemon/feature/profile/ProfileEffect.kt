package com.example.littlelemon.feature.profile

sealed interface ProfileEffect {
    data object NavigateToOnboarding : ProfileEffect
}
