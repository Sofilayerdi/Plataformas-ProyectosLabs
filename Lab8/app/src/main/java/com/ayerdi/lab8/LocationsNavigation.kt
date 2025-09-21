package com.ayerdi.lab8

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

@Serializable
object LocationsGraph

@Serializable
object Locations

@Serializable
data class LocationDetails(val locationId: Int)

fun NavGraphBuilder.locationsNavigation(
    onNavigateToLocationDetails: (Int) -> Unit,
    onNavigateBack: () -> Unit
) {
    navigation<LocationsGraph>(startDestination = Locations) {
        composable<Locations> {
            LocationsScreen(
                onLocationClick = onNavigateToLocationDetails
            )
        }

        composable<LocationDetails> { backStackEntry ->
            val destination = backStackEntry.toRoute<LocationDetails>()
            LocationDetailsScreen(
                locationId = destination.locationId,
                onBack = onNavigateBack
            )
        }
    }
}