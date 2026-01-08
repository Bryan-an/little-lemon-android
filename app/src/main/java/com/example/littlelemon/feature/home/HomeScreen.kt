package com.example.littlelemon.feature.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.littlelemon.R
import com.example.littlelemon.ui.theme.LittleLemonTheme

private const val HOME_LOGO_WIDTH_FRACTION = 0.5f

@Composable
fun HomeScreen(
    onNavigateToProfile: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            Surface {
                Box(
                    modifier =
                    Modifier
                        .fillMaxWidth()
                        .statusBarsPadding()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                ) {
                    Image(
                        painter = painterResource(R.drawable.little_lemon_logo),
                        contentDescription = stringResource(R.string.logo_content_description),
                        contentScale = ContentScale.Fit,
                        modifier =
                        Modifier
                            .align(Alignment.Center)
                            .height(40.dp)
                            .fillMaxWidth(HOME_LOGO_WIDTH_FRACTION),
                    )

                    IconButton(
                        onClick = onNavigateToProfile,
                        modifier = Modifier.align(Alignment.CenterEnd),
                    ) {
                        Image(
                            painter = painterResource(R.drawable.profile),
                            contentDescription =
                            stringResource(R.string.profile_icon_content_description),
                            contentScale = ContentScale.Fit,
                            modifier = Modifier.size(40.dp),
                        )
                    }
                }
            }
        },
    ) { innerPadding ->
        Column(
            modifier =
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(R.string.home_screen),
                style = MaterialTheme.typography.headlineMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    LittleLemonTheme {
        HomeScreen(
            onNavigateToProfile = {},
        )
    }
}
