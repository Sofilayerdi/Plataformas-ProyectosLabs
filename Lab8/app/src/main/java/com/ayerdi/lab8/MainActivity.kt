package com.ayerdi.lab8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.Image
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.TestModifierUpdaterLayout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ayerdi.lab8.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    var pantallaActual by remember { mutableStateOf("Login") }
    var characterSelectedId by remember { mutableStateOf<Int?>(null) }

    when (pantallaActual) {
        "Login" -> {
            Login(
                onLoginClick = {
                    pantallaActual = "Characters"
                },
                modifier = modifier
            )
        }
        "Characters" -> {
            Characters(
                onCharacterClick = { characterId ->
                    characterSelectedId = characterId
                    pantallaActual = "CharacterDetails"
                },
                modifier = modifier
            )
        }
        "CharacterDetails" -> {
            characterSelectedId?.let { id ->
                CharacterDetails(
                    characterId = id,
                    onBack = {
                        pantallaActual = "Characters"
                    },
                    modifier = modifier
                )
            }
        }
    }
}


@Composable
fun Login(
    onLoginClick: () -> Unit = {},
    modifier: Modifier = Modifier) {

    val colors = MaterialTheme.colorScheme

    Box(
        modifier = modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.rm),
                contentDescription = "Rick and Morty",
                contentScale = ContentScale.Fit,
                alpha = 0.3f
            )
            Button(
                onClick = onLoginClick,
                modifier = Modifier
                    .width(260.dp)
                    .height(64.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = "Entrar",
                    fontSize = 20.sp
                )
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Sofia Lopez - 231929")
        }
    }
}

@Composable
fun Characters(
    onCharacterClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
){
    val colors = MaterialTheme.colorScheme
    val characterDb = remember { CharacterDb() }
    val characters = characterDb.getAllCharacters()

    Box(
        modifier = modifier
            .fillMaxWidth()
    ){
        Column (
            modifier = Modifier
                .fillMaxSize()
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(colors.primaryContainer)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Characters",
                    fontSize = 24.sp
                )
            }
            LazyColumn (
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
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
fun CharacterItem(
    character: Character,
    onClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier
){
    val colors = MaterialTheme.colorScheme

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onClick(character.id) }
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.Top
    ){
        Box(
            modifier = Modifier
                .size(60.dp)
                .background(color = colors.inversePrimary, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {}

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
fun CharacterDetails(
    characterId: Int,
    onBack: () -> Unit = {},
    modifier: Modifier = Modifier
){

    val colors = MaterialTheme.colorScheme
    val characterDb = remember { CharacterDb() }
    val character = remember(characterId) { characterDb.getCharacterById(characterId) }

    Box(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize(),
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
                IconButton(
                    onClick = onBack,
                ){
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
            ){
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(
                            color = colors.inversePrimary,
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ){}

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
            ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ){
                    Text(
                        text = "Species:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    )


                    Text(
                        text = character.species,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ){
                    Text(
                        text = "Status:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = character.status,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ){
                    Text(
                        text = "Gender:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = character.gender,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    )
                }

            }




        }
    }
}


@Preview(showBackground = true)
@Composable
fun LoginPreview() {
    AppTheme {
        Login()
    }
}

@Preview(showBackground = true)
@Composable
fun CharactersPreview() {
    AppTheme {
        Characters()
    }
}

@Preview(showBackground = true)
@Composable
fun CharacterDetailsPreview() {
    AppTheme {
        CharacterDetails(characterId = 1, onBack = { })
    }
}