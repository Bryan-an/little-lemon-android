package com.example.littlelemon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.feature.onboarding.OnboardingRoute

@Composable
fun LittleLemonNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = LittleLemonRoutes.ONBOARDING,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(LittleLemonRoutes.ONBOARDING) {
            OnboardingRoute()
        }
    }
}
