package com.ayerdi.lab8

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.toRoute
import kotlinx.serialization.Serializable

// Sofia Lopez - 231929

@Serializable
object CharactersGraph

@Serializable
object Characters

@Serializable
data class CharacterDetails(val characterId: Int)

fun NavGraphBuilder.charactersNavigation(
    onNavigateToCharacterDetails: (Int) -> Unit,
    onNavigateBack: () -> Unit
) {
    navigation<CharactersGraph>(startDestination = Characters) {
        composable<Characters> {
            CharactersScreen(
                onCharacterClick = onNavigateToCharacterDetails
            )
        }

        composable<CharacterDetails> {
            CharacterDetailsScreen(
                onBack = onNavigateBack
            )
        }
    }
}