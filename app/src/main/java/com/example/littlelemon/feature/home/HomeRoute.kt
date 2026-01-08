package com.example.littlelemon.feature.home

import androidx.compose.runtime.Composable

@Composable
fun HomeRoute(
    onNavigateToProfile: () -> Unit,
) {
    HomeScreen(
        onNavigateToProfile = onNavigateToProfile,
    )
}
