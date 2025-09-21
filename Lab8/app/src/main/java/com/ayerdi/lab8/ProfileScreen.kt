package com.ayerdi.lab8

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.CircleShape
import com.ayerdi.lab8.R

@Composable
fun ProfileScreen(
    onLogout: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme

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
                Text(
                    text = "Profile",
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
                Image(
                    painter = painterResource(id = R.drawable.perfil),
                    contentDescription = "Foto usuario",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .clip(CircleShape)
                )

                Spacer(Modifier.height(14.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp)
                    .padding(horizontal = 50.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Text(
                        text = "Nombre:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = "Sofia Lopez Ayerdi",
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
                ) {
                    Text(
                        text = "Carne:",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = "231929",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Light
                    )
                }

                Spacer(Modifier.height(32.dp))
            }

            Button(
                onClick = onLogout,
                shape = RoundedCornerShape(50),
                border = BorderStroke(1.dp, colors.primaryContainer),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = colors.primaryContainer
                ),
                modifier = Modifier
                    .height(48.dp)
                    .width(180.dp)
            ) {
                Text("Cerrar sesión")
            }
        }
    }
}