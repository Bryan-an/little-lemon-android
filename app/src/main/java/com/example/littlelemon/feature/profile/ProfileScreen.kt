package com.example.littlelemon.feature.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonTheme

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    onLogoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
    ) { innerPadding ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            ProfileHeaderLogo(
                modifier = Modifier.padding(top = 16.dp),
            )

            Spacer(modifier = Modifier.height(8.dp))

            ProfileInformationSection(
                uiState = uiState,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.weight(1f))

            LogoutButton(
                onClick = onLogoutClick,
                modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            )
        }
    }
}

@Composable
private fun ProfileHeaderLogo(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.little_lemon_logo),
        contentDescription = stringResource(R.string.logo_content_description),
        contentScale = ContentScale.Fit,
        modifier =
        modifier
            .height(64.dp)
            .fillMaxWidth()
            .widthIn(max = 320.dp),
    )
}

@Composable
private fun ProfileInformationSection(
    uiState: ProfileUiState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = stringResource(R.string.profile_information),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.semantics { heading() },
        )

        Text(
            text = "${stringResource(R.string.first_name)}: ${uiState.firstName}",
            style = MaterialTheme.typography.bodyLarge,
        )

        Text(
            text = "${stringResource(R.string.last_name)}: ${uiState.lastName}",
            style = MaterialTheme.typography.bodyLarge,
        )

        Text(
            text = "${stringResource(R.string.email)}: ${uiState.email}",
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun LogoutButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Text(text = stringResource(R.string.logout))
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileScreenPreview() {
    LittleLemonTheme {
        ProfileScreen(
            uiState =
            ProfileUiState(
                firstName = "Tilly",
                lastName = "Doe",
                email = "tillydoe@example.com",
            ),
            onLogoutClick = {},
        )
    }
}
