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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayerdi.lab8.Character
import com.ayerdi.lab8.CharacterDb

@Composable
fun CharactersScreen(
    onCharacterClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val characterDb = remember { CharacterDb() }
    val characters = characterDb.getAllCharacters()

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
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
            LazyColumn(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(characters) { character ->
                    CharacterItem(
                        character = character,
                        onClick = onCharacterClick
                    )
                }
            }
        }
    }
}

@Composable
fun CharactersItem(
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
            // AsyncImage placeholder
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
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    val characterDb = remember { CharacterDb() }
    val character = remember(characterId) { characterDb.getCharacterById(characterId) }

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
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
                    // AsyncImage placeholder
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