package com.example.littlelemon.feature.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonTheme

private const val LOGO_WIDTH_FRACTION = 0.7f

@Suppress("LongMethod")
@Composable
fun OnboardingScreen(
    uiState: OnboardingUiState,
    actions: OnboardingActions,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    val lastNameFocusRequester = remember { FocusRequester() }
    val emailFocusRequester = remember { FocusRequester() }

    var firstNameTouched by rememberSaveable { mutableStateOf(false) }
    var lastNameTouched by rememberSaveable { mutableStateOf(false) }
    var emailTouched by rememberSaveable { mutableStateOf(false) }

    val firstNameError = if (firstNameTouched) uiState.firstNameError else null
    val lastNameError = if (lastNameTouched) uiState.lastNameError else null
    val emailError = if (emailTouched) uiState.emailError else null

    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier =
            Modifier
                .padding(innerPadding)
                .verticalScroll(scrollState)
                .imePadding()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            OnboardingLogo(
                modifier =
                Modifier
                    .padding(top = 24.dp)
                    .align(Alignment.CenterHorizontally),
            )

            OnboardingTitleBanner()
            PersonalInformationHeader()

            FirstNameTextField(
                value = uiState.firstName,
                error = firstNameError,
                onValueChange = {
                    firstNameTouched = true
                    actions.onFirstNameChanged(it)
                },
                onNext = { lastNameFocusRequester.requestFocus() },
            )

            LastNameTextField(
                value = uiState.lastName,
                error = lastNameError,
                onValueChange = {
                    lastNameTouched = true
                    actions.onLastNameChanged(it)
                },
                onNext = { emailFocusRequester.requestFocus() },
                modifier = Modifier.focusRequester(lastNameFocusRequester),
            )

            EmailTextField(
                value = uiState.email,
                error = emailError,
                onValueChange = {
                    emailTouched = true
                    actions.onEmailChanged(it)
                },
                onDone = { focusManager.clearFocus() },
                modifier = Modifier.focusRequester(emailFocusRequester),
            )

            RegisterButton(
                onClick = {
                    firstNameTouched = true
                    lastNameTouched = true
                    emailTouched = true
                    actions.onRegisterClick()
                },
            )
        }
    }
}

@Composable
private fun OnboardingLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.little_lemon_logo),
        contentDescription = stringResource(R.string.logo_content_description),
        contentScale = ContentScale.Fit,
        modifier =
        modifier
            .height(72.dp)
            .fillMaxWidth(LOGO_WIDTH_FRACTION),
    )
}

@Composable
private fun OnboardingTitleBanner(modifier: Modifier = Modifier) {
    Surface(
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.fillMaxWidth(),
    ) {
        Box(
            modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = stringResource(R.string.onboarding_title),
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.onPrimary,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
private fun PersonalInformationHeader(modifier: Modifier = Modifier) {
    Text(
        text = stringResource(R.string.personal_information),
        style = MaterialTheme.typography.titleSmall,
        color = MaterialTheme.colorScheme.onBackground,
        modifier = modifier.semantics { heading() },
    )
}

@Composable
private fun FirstNameTextField(
    value: String,
    error: FieldError?,
    onValueChange: (String) -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val errorMessage = error?.let { stringResource(it.messageRes) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text(text = stringResource(R.string.first_name)) },
        isError = errorMessage != null,
        singleLine = true,
        supportingText = errorMessage?.let { { Text(text = it) } },
        keyboardOptions =
        KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            imeAction = ImeAction.Next,
        ),
        keyboardActions = KeyboardActions(onNext = { onNext() }),
    )
}

@Composable
private fun LastNameTextField(
    value: String,
    error: FieldError?,
    onValueChange: (String) -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val errorMessage = error?.let { stringResource(it.messageRes) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text(text = stringResource(R.string.last_name)) },
        isError = errorMessage != null,
        singleLine = true,
        supportingText = errorMessage?.let { { Text(text = it) } },
        keyboardOptions =
        KeyboardOptions(
            capitalization = KeyboardCapitalization.Words,
            imeAction = ImeAction.Next,
        ),
        keyboardActions = KeyboardActions(onNext = { onNext() }),
    )
}

@Composable
private fun EmailTextField(
    value: String,
    error: FieldError?,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val errorMessage = error?.let { stringResource(it.messageRes) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier.fillMaxWidth(),
        label = { Text(text = stringResource(R.string.email)) },
        isError = errorMessage != null,
        singleLine = true,
        supportingText = errorMessage?.let { { Text(text = it) } },
        keyboardOptions =
        KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done,
            autoCorrectEnabled = false,
        ),
        keyboardActions = KeyboardActions(onDone = { onDone() }),
    )
}

@Composable
private fun RegisterButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilledTonalButton(
        onClick = onClick,
        modifier =
        modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 24.dp),
        contentPadding = PaddingValues(vertical = 14.dp),
    ) {
        Text(
            text = stringResource(R.string.register),
            style = MaterialTheme.typography.titleMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun OnboardingScreenPreview() {
    LittleLemonTheme {
        OnboardingScreen(
            uiState =
            OnboardingUiState(
                firstName = "Tilly",
                lastName = "Doe",
                email = "tillydoe@example.com",
                isFormValid = true,
            ),
            actions =
            OnboardingActions(
                onFirstNameChanged = {},
                onLastNameChanged = {},
                onEmailChanged = {},
                onRegisterClick = {},
            ),
        )
    }
}
