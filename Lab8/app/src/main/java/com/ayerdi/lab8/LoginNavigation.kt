package com.ayerdi.lab8

import androidx.activity.compose.BackHandler
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import kotlin.system.exitProcess

// Sofia Lopez - 231929

@Serializable
object Login

fun NavGraphBuilder.loginNavigation(
    onLoginClick: () -> Unit
) {
    composable<Login> {
        BackHandler {
            exitProcess(0)
        }

        LoginScreen(
            onLoginClick = onLoginClick
        )
    }
}