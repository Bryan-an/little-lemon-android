package com.example.littlelemon.feature.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.littlelemon.data.local.UserPreferences
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow

class ProfileViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val userPreferences = UserPreferences(application)

    private val _uiState =
        MutableStateFlow(
            ProfileUiState(
                firstName = userPreferences.getFirstName(),
                lastName = userPreferences.getLastName(),
                email = userPreferences.getEmail(),
            ),
        )

    val uiState: StateFlow<ProfileUiState> = _uiState

    private val _effects = MutableSharedFlow<ProfileEffect>(extraBufferCapacity = 1)
    val effects: SharedFlow<ProfileEffect> = _effects.asSharedFlow()

    fun onLogoutClick() {
        userPreferences.logout()
        _uiState.value = ProfileUiState()
        _effects.tryEmit(ProfileEffect.NavigateToOnboarding)
    }
}
