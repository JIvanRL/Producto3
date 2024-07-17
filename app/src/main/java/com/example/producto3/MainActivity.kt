package com.example.producto3

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.tv.foundation.lazy.list.TvLazyColumn
import androidx.tv.foundation.lazy.list.items
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import com.example.producto3.ui.theme.Producto3Theme
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.TextStyle
import java.util.Locale
import androidx.compose.ui.graphics.Color.Companion.Black as Black1

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Producto3Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    shape = RectangleShape
                ) {
                    Navegacion()
                }
            }
        }
    }
}

@Composable
fun Imagen(id: Int) {
    Image(
        painter = painterResource(id = id),
        contentDescription = null,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun FondoConDegradadoRadial(showImage: Boolean = true) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (showImage) {
            Imagen(R.drawable.fondo)
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("DefaultLocale")
@Composable
fun Portada(navController: NavController) {
    val currentDate = LocalDate.now()
    val year = currentDate.year
    val month = currentDate.monthValue
    val day = currentDate.dayOfMonth
    val dayOfWeek = currentDate.dayOfWeek.getDisplayName(TextStyle.FULL, Locale("es", "ES"))

    val currentTime = LocalDateTime.now()
    val hour = currentTime.hour
    val minute = currentTime.minute
    val amPm = if (hour < 12) "AM" else "PM"
    val hour12 = if (hour > 12) hour - 12 else if (hour == 0) 12 else hour

    var weather by remember { mutableStateOf<WeatherResponse?>(null) }
    var error by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val response = RetrofitInstance.api.getCurrentWeather("Madrid,ES", "03cb430e428bf50a9e9879884efa7de8")
                weather = response
            } catch (e: Exception) {
                error = e.localizedMessage
                println("Error fetching weather: ${e.localizedMessage}")
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        FondoConDegradadoRadial(showImage = true)
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "☺ Copy Rigth Ivan",
                color = Color.White,
                fontWeight = FontWeight.Light,
                textAlign = TextAlign.Right,
                modifier = Modifier
                    .padding(16.dp)
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Black1.copy(alpha = 0.10f),
                                Black1.copy(alpha = 0.10f),
                                Color.Transparent
                            )
                        )
                    )
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$dayOfWeek, $day/$month/$year",
                color = Color.White,
                fontWeight = FontWeight.Light,
                fontSize = 30.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(10.dp)
            )
            Text(
                text = String.format("%02d:%02d %s", hour12, minute, amPm),
                color = Color.White,
                fontWeight = FontWeight.Light,
                fontSize = 60.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(5.dp)
            )
            weather?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Temp: ${it.main.temp}°C, ${it.weather.first().description.capitalize()}",
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(5.dp)
                )
            }
            error?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Error: $it",
                    color = Color.Red,
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}
// Lista de aplicaciones simulada
val appList = listOf(
    "YouTube",
    "Netflix",
    "Prime Video",
    "Hulu",
    "Disney+",
    "Spotify",
    "Twitch",
    "ESPN"
)

@Composable
fun SmartTvScreen() {
    Surface() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Apps",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
            AppList(appList = appList)
        }
    }
}

@Composable
fun AppList(appList: List<String>) {
    TvLazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(appList) { app ->
            AppListItem(appName = app)
        }
    }
}

@Composable
fun AppListItem(appName: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(color = Color.DarkGray)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = appName,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navegacion() {
    val navController = rememberNavController() // Recordar el controlador de navegación
    NavHost(navController, startDestination = "RutaUno") {
        composable("RutaUno") { Portada(navController) }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Producto3Theme {
        Navegacion()
    }
}