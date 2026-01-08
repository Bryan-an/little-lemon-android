package com.example.littlelemon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.feature.home.HomeRoute
import com.example.littlelemon.feature.onboarding.OnboardingRoute
import com.example.littlelemon.feature.profile.ProfileRoute

@Composable
fun LittleLemonNavHost(
    navController: NavHostController,
    onShowMessage: (Int) -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = LittleLemonRoutes.ONBOARDING,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(LittleLemonRoutes.ONBOARDING) {
            OnboardingRoute(
                onShowMessage = onShowMessage,
                onNavigateToHome = {
                    navController.navigate(LittleLemonRoutes.HOME) {
                        launchSingleTop = true
                    }
                },
            )
        }

        composable(LittleLemonRoutes.HOME) {
            HomeRoute(
                onNavigateToProfile = { navController.navigate(LittleLemonRoutes.PROFILE) },
            )
        }

        composable(LittleLemonRoutes.PROFILE) {
            ProfileRoute(
                onNavigateToOnboarding = {
                    navController.navigate(LittleLemonRoutes.ONBOARDING) {
                        launchSingleTop = true
                    }
                },
            )
        }
    }
}
