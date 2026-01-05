package com.example.littlelemon.feature.onboarding

import androidx.annotation.StringRes
import com.example.littlelemon.R

sealed interface FieldError {
    @get:StringRes val messageRes: Int

    data object Required : FieldError {
        override val messageRes: Int = R.string.error_required
    }

    data object InvalidEmail : FieldError {
        override val messageRes: Int = R.string.error_invalid_email
    }
}

data class OnboardingUiState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val firstNameError: FieldError? = null,
    val lastNameError: FieldError? = null,
    val emailError: FieldError? = null,
    val isFormValid: Boolean = false,
)
