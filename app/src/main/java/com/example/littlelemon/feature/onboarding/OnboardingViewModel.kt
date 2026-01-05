package com.example.littlelemon.feature.onboarding

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class OnboardingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState

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
        // Intentionally no-op for now; submission/navigation will be implemented later.
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
