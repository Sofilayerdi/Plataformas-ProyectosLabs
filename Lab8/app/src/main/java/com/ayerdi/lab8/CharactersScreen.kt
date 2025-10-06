package com.ayerdi.lab8

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun CharactersScreen(
    onCharacterClick: (Int) -> Unit = {},
    viewModel: CharactersViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CharactersScreenContent(
        state = state,
        onCharacterClick = onCharacterClick,
        onRetry = { viewModel.loadCharacters() },
        modifier = modifier
    )
}

@Composable
private fun CharactersScreenContent(
    state: CharactersState,
    onCharacterClick: (Int) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Box(modifier = modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.primaryContainer)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Characters",
                    fontSize = 24.sp
                )
            }

            // Content según estado
            when {
                state.isLoading -> {
                    LoadingScreen(modifier = Modifier.fillMaxSize())
                }
                state.hasError -> {
                    ErrorScreen(
                        onRetry = onRetry,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> {
                    LazyColumn(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(state.data) { character ->
                            CharacterItem(
                                character = character,
                                onClick = onCharacterClick
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterItem(
    character: Character,
    onClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(character.id) }
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(color = colors.inversePrimary, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = character.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "${character.species} - ${character.status}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Light,
            )
        }
    }
}

@Composable
fun CharacterDetailsScreen(
    characterId: Int,
    onBack: () -> Unit = {},
    viewModel: CharacterDetailViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CharacterDetailsContent(
        state = state,
        onBack = onBack,
        onRetry = { viewModel.loadCharacter() },
        modifier = modifier
    )
}

@Composable
private fun CharacterDetailsContent(
    state: CharacterDetailState,
    onBack: () -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.primaryContainer)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = colors.onPrimaryContainer
                    )
                }
                Text(
                    text = "Characters",
                    fontSize = 24.sp
                )
            }

            when {
                state.isLoading -> {
                    LoadingScreen(modifier = Modifier.fillMaxSize())
                }
                state.hasError -> {
                    ErrorScreen(
                        onRetry = onRetry,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                state.data != null -> {
                    val character = state.data
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Box(
                            modifier = Modifier
                                .size(150.dp)
                                .background(
                                    color = colors.inversePrimary,
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                        }

                        Spacer(Modifier.height(14.dp))

                        Text(
                            text = character.name,
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 20.dp)
                            .padding(horizontal = 50.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        DetailRow("Species:", character.species)
                        DetailRow("Status:", character.status)
                        DetailRow("Gender:", character.gender)
                    }
                }
            }
        }
    }
}

@Composable
private fun DetailRow(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = label,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light
        )
        Text(
            text = value,
            fontSize = 18.sp,
            fontWeight = FontWeight.Light
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCharacterDetailsScreen() {
    CharacterDetailsContent(
        state = CharacterDetailState(
            isLoading = false,
            data = Character(1, "Rick Sanchez", "Alive", "Human", "Male", ""),
            hasError = false
        ),
        onBack = {},
        onRetry = {}
    )
}