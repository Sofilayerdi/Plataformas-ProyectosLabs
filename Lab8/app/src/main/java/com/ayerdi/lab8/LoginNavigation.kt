package com.ayerdi.lab8

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Login

fun NavGraphBuilder.loginNavigation(
    onLoginClick: () -> Unit
) {
    composable<Login> {
        LoginScreen(
            onLoginClick = onLoginClick
        )
    }
}