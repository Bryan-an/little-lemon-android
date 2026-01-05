package com.example.littlelemon.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.littlelemon.ui.navigation.LittleLemonNavHost

@Composable
fun LittleLemonApp(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    LittleLemonNavHost(
        navController = navController,
        modifier = modifier,
    )
}
