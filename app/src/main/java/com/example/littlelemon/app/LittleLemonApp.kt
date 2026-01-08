package com.example.littlelemon.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.data.local.UserPreferences
import com.example.littlelemon.ui.navigation.LittleLemonNavHost
import com.example.littlelemon.ui.navigation.LittleLemonRoutes
import kotlinx.coroutines.launch

@Composable
fun LittleLemonApp(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val userPreferences = remember(context) { UserPreferences(context.applicationContext) }

    val isLoggedIn =
        userPreferences
            .observeIsLoggedIn()
            .collectAsStateWithLifecycle(initialValue = userPreferences.isLoggedIn())

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val startDestination =
        if (isLoggedIn.value) LittleLemonRoutes.HOME else LittleLemonRoutes.ONBOARDING

    val showMessage: (Int) -> Unit =
        remember(snackbarHostState, context, scope) {
            { messageRes ->
                scope.launch {
                    snackbarHostState.showSnackbar(
                        message = context.getString(messageRes),
                    )
                }
            }
        }

    Box(modifier = modifier.fillMaxSize()) {
        key(isLoggedIn.value) {
            val navController = rememberNavController()

            LittleLemonNavHost(
                navController = navController,
                modifier = Modifier.fillMaxSize(),
                startDestination = startDestination,
                onShowMessage = showMessage,
            )
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier =
            Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .imePadding(),
        )
    }
}
