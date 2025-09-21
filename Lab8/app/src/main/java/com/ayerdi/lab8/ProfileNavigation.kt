package com.ayerdi.lab8

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
object Profile

fun NavGraphBuilder.profileNavigation(
    onLogout: () -> Unit
) {
    composable<Profile> {
        ProfileScreen(
            onLogout = onLogout
        )
    }
}