package com.example.producto3

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Text


// Lista de aplicaciones simulada
val appList = listOf(
    "YouTube",
    "Netflix",
    "Prime Video",
    "Hulu",
    "Disney+",
    "Spotify",
    "Twitch",
    "ESPN",
    "Full",
    "Play Store" ,
    "Google"
)
//lista de las aplicaciones
@Composable
fun AppListItem(appName: String, onAppClick: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(vertical = 2.dp)
            .background(color = Color.Transparent)
            .padding(horizontal = 10.dp)
            .clickable { onAppClick(appName) } // Añadir onClick aquí
    ) {
        Image(
            painter = painterResource(id = getAppImageResource(appName)),
            contentDescription = appName,
            modifier = Modifier.size(70.dp) // Ajusta el tamaño según sea necesario
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = appName,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White
        )
    }
}