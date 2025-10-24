package com.ayerdi.lab8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.activity.compose.BackHandler
import com.ayerdi.lab8.ui.theme.AppTheme
import kotlinx.serialization.Serializable

// Sofia Lopez - 231929

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                AppNavigation()
            }
        }
    }
}

@Serializable
object Splash

@Serializable
object MainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Splash
    ) {
        composable<Splash> {
            SplashScreen(
                onNavigateToLogin = {
                    navController.navigate(Login) {
                        popUpTo<Splash> { inclusive = true }
                    }
                },
                onNavigateToMain = {
                    navController.navigate(MainScreen) {
                        popUpTo<Splash> { inclusive = true }
                    }
                }
            )
        }

        // Login Navigation
        loginNavigation(
            onLoginClick = {
                navController.navigate(MainScreen) {
                    popUpTo<Login> { inclusive = true }
                }
            }
        )

        // Main App Screen
        composable<MainScreen> {
            // BackHandler para cerrar la app en lugar de regresar
            BackHandler {
                // No hacer nada - esto previene el back a login
                // La app se cerrará con el back del sistema
            }

            MainAppScreen(
                onLogout = {
                    navController.navigate(Login) {
                        popUpTo<MainScreen> { inclusive = true }
                    }
                }
            )
        }
    }
}

@Composable
fun MainAppScreen(
    onLogout: () -> Unit
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Face, contentDescription = "Characters") },
                    label = { Text("Characters") },
                    selected = currentDestination?.contains("Characters") == true,
                    onClick = {
                        navController.navigate(CharactersGraph) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.LocationOn, contentDescription = "Locations") },
                    label = { Text("Locations") },
                    selected = currentDestination?.contains("Locations") == true,
                    onClick = {
                        navController.navigate(LocationsGraph) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = currentDestination?.contains("Profile") == true,
                    onClick = {
                        navController.navigate(Profile) {
                            popUpTo(navController.graph.startDestinationId)
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CharactersGraph,
            modifier = Modifier.padding(innerPadding)
        ) {
            charactersNavigation(
                onNavigateToCharacterDetails = { characterId ->
                    navController.navigate(CharacterDetails(characterId = characterId))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )

            locationsNavigation(
                onNavigateToLocationDetails = { locationId ->
                    navController.navigate(LocationDetails(locationId = locationId))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )

            profileNavigation(
                onLogout = onLogout
            )
        }
    }
}