package com.ayerdi.lab8

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import com.ayerdi.lab8.ui.theme.AppTheme
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun ErrorScreen(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .border(
                    width = 3.dp,
                    color = Color.Red,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "!",
                fontSize = 48.sp,
                color = Color.Red
            )
        }


        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Error al obtener listado de personajes.",
            fontSize = 16.sp,
            color = Color.Red,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Text(
            text = "Intenta de nuevo",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = Color.Red,
            modifier = Modifier.padding(horizontal = 32.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedButton(
            onClick = onRetry,
            modifier = Modifier
                .width(200.dp)
                .height(48.dp)
        ) {
            Text(
                text = "Reintentar",
                fontSize = 16.sp,
                color = Color.Red
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewErrorScreen() {
    ErrorScreen(onRetry = {})
}
