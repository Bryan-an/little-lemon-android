package com.example.littlelemon.feature.onboarding

import android.app.Application
import android.util.Patterns
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.littlelemon.R
import com.example.littlelemon.data.local.UserPreferences
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OnboardingViewModel(
    application: Application,
) : AndroidViewModel(application) {
    private val userPreferences = UserPreferences(application)

    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState

    private val _effects = MutableSharedFlow<OnboardingEffect>(extraBufferCapacity = 1)
    val effects: SharedFlow<OnboardingEffect> = _effects.asSharedFlow()

    fun onFirstNameChanged(value: String) {
        _uiState.update { it.copy(firstName = value).validated() }
    }

    fun onLastNameChanged(value: String) {
        _uiState.update { it.copy(lastName = value).validated() }
    }

    fun onEmailChanged(value: String) {
        _uiState.update { it.copy(email = value).validated() }
    }

    fun onRegisterClick() {
        val state = _uiState.value.validated()
        _uiState.value = state

        if (!state.isFormValid) {
            _effects.tryEmit(OnboardingEffect.ShowMessage(R.string.registration_unsuccessful))
            return
        }

        userPreferences.saveUser(
            firstName = state.firstName.trim(),
            lastName = state.lastName.trim(),
            email = state.email.trim(),
        )

        viewModelScope.launch {
            _effects.emit(OnboardingEffect.ShowMessage(R.string.registration_successful))
            _effects.emit(OnboardingEffect.NavigateToHome)
        }
    }

    private fun OnboardingUiState.validated(): OnboardingUiState {
        val trimmedFirstName = firstName.trim()
        val trimmedLastName = lastName.trim()
        val trimmedEmail = email.trim()

        val firstNameError = if (trimmedFirstName.isBlank()) FieldError.Required else null
        val lastNameError = if (trimmedLastName.isBlank()) FieldError.Required else null

        val emailError =
            when {
                trimmedEmail.isBlank() -> FieldError.Required
                Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches() -> null
                else -> FieldError.InvalidEmail
            }

        val isFormValid = firstNameError == null && lastNameError == null && emailError == null

        return copy(
            firstNameError = firstNameError,
            lastNameError = lastNameError,
            emailError = emailError,
            isFormValid = isFormValid,
        )
    }
}
