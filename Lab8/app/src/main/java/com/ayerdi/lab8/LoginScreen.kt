package com.ayerdi.lab8

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ayerdi.lab8.R


// Sofia Lopez - 231929

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    viewModel: LoginViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val state by viewModel.state.collectAsState()

    LoginScreenContent(
        name = state.name,
        isLoading = state.isLoading,
        onNameChange = { viewModel.onNameChange(it) },
        onLoginClick = {
            viewModel.login(
                onSuccess = onLoginClick
            )
        },
        modifier = modifier
    )
}

@Composable
private fun LoginScreenContent(
    name: String,
    isLoading: Boolean,
    onNameChange: (String) -> Unit,
    onLoginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(bottom = 80.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.rm),
                contentDescription = "Rick and Morty",
                contentScale = ContentScale.Fit,
                alpha = 0.3f
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = name,
                onValueChange = onNameChange,
                label = { Text("Nombre") },
                enabled = !isLoading,
                singleLine = true,
                modifier = Modifier
                    .width(280.dp)
                    .padding(bottom = 16.dp)
            )

            Button(
                onClick = onLoginClick,
                enabled = !isLoading && name.isNotBlank(),
                modifier = Modifier
                    .width(260.dp)
                    .height(64.dp),
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(24.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                } else {
                    Text(
                        text = "Entrar",
                        fontSize = 20.sp
                    )
                }
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

@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreen() {
    LoginScreenContent(
        name = "Sofia",
        isLoading = false,
        onNameChange = {},
        onLoginClick = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewLoginScreenLoading() {
    LoginScreenContent(
        name = "Sofia",
        isLoading = true,
        onNameChange = {},
        onLoginClick = {}
    )
}