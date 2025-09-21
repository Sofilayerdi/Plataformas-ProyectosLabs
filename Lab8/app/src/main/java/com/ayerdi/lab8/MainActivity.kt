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
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ayerdi.lab8.ui.theme.AppTheme
import com.ayerdi.lab8.Login
import com.ayerdi.lab8.loginNavigation
import com.ayerdi.lab8.CharactersGraph
import com.ayerdi.lab8.charactersNavigation
import com.ayerdi.lab8.LocationsGraph
import com.ayerdi.lab8.locationsNavigation
import com.ayerdi.lab8.Profile
import com.ayerdi.lab8.profileNavigation
import kotlinx.serialization.Serializable

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
object MainScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Login
    ) {
        loginNavigation(
            onLoginClick = {
                navController.navigate(MainScreen) {
                    popUpTo<Login> { inclusive = true }
                }
            }
        )

        composable<MainScreen> {
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
                    navController.navigate(com.ayerdi.lab8.CharacterDetails(characterId = characterId))
                },
                onNavigateBack = {
                    navController.popBackStack()
                }
            )

            locationsNavigation(
                onNavigateToLocationDetails = { locationId ->
                    navController.navigate(com.ayerdi.lab8.LocationDetails(locationId = locationId))
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