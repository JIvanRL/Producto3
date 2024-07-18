package com.example.producto3

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
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
import androidx.tv.foundation.lazy.list.TvLazyRow
import androidx.tv.foundation.lazy.list.rememberTvLazyListState
import androidx.tv.material3.Button
import androidx.tv.material3.ButtonDefaults
import androidx.tv.material3.MaterialTheme
import androidx.tv.material3.Surface
import androidx.tv.material3.Text
import com.example.producto3.ui.theme.Producto3Theme
import kotlinx.coroutines.delay
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
@Composable
fun Portada(navController: NavController) {
    // Variables para mostrar la fecha y hora actual
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

    // Estructura visual de la pantalla de portada
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                navController.navigate("RutaDos")
            }
    ) {
        // Fondo con degradado radial
        FondoConDegradadoRadial(showImage = true)

        // Contenido centrado verticalmente en la parte inferior de la pantalla
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Texto de derechos de autor
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

        // Contenido principal centrado verticalmente en la parte superior de la pantalla
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Fecha y hora
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

            // Información del clima (opcional)
            weather?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Temp: ${it.main.temp}°C, ${it.weather.first().description.capitalize()}",
                    color = Color.White,
                    fontWeight = FontWeight.Light,
                    fontSize = 50.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(5.dp)
                )
            }

            // Manejo de errores (opcional)
            error?.let {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Error: $it",
                    color = Color.Red,
                    fontWeight = FontWeight.Light,
                    fontSize = 50.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}


// Lista de imágenes simulada
val imageList = listOf(
    R.drawable.imagen2,
    R.drawable.imagen3,
    R.drawable.imagen4,
    R.drawable.imagen5,
    R.drawable.imagen6,
    R.drawable.imagen7,
    // Agrega más imágenes aquí
)

@Composable
fun SmartTvScreen(navController: NavController, context: Context) {
    // Lista de imágenes para el carrusel (asegúrate de definir esto adecuadamente)

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // Botones con transparencia ligera
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Start
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.Start
                ) {
                    TransparentButton2(text = "Perfil")
                    Spacer(modifier = Modifier.width(8.dp))
                    TransparentButton2(text = "Inicio")
                    Spacer(modifier = Modifier.width(8.dp))
                    TransparentButton2(text = "TV")
                    Spacer(modifier = Modifier.width(8.dp))
                    TransparentButton2(text = "Configuración")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TransparentButton2(text = "Buscar")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Carrusel de imágenes con desplazamiento automático
            AutoScrollingCarousel(imageList = imageList)

            Spacer(modifier = Modifier.height(16.dp))

            // Otro carrusel de imágenes (ajustado a 120.dp)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp) // Ajusta la altura del carrusel según sea necesario
            ) {
                TvLazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(imageList.size) { index ->
                        val imageResId = imageList[index]
                        Image(
                            painter = painterResource(id = imageResId),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxHeight()
                                .size(120.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Contenedor de aplicaciones
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                TvLazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(appList.size) { index ->
                        val app = appList[index]
                        AppListItem(appName = app) { selectedApp ->
                            when (selectedApp) {
                                "YouTube" -> openYouTubeApp(context)
                                "Spotify" -> openSpotifyApp(context)
                                "Netflix" -> openNetflixApp(context)
                                "Google" -> openGoogleApp(context)
                                else -> navController.navigate("detail_screen/$selectedApp")
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AutoScrollingCarousel(imageList: List<Int>) {
    val scrollState = rememberTvLazyListState() // Usa TvLazyListState en lugar de LazyListState
    val coroutineScope = rememberCoroutineScope()

    // Ajusta la velocidad y el intervalo del desplazamiento automático aquí
    val scrollInterval = 3000L // 3 segundos entre desplazamientos
    val scrollAmount = 1 // Cuántos elementos se deben desplazar cada vez

    // Configura el efecto de lanzamiento para el desplazamiento automático
    LaunchedEffect(Unit) {
        while (true) {
            delay(scrollInterval) // Espera el intervalo
            val nextItemIndex = (scrollState.firstVisibleItemIndex + scrollAmount) % imageList.size
            scrollState.animateScrollToItem(nextItemIndex) // Anima el desplazamiento
        }
    }

    Box(
        modifier = Modifier
                       .background(Color.Transparent)
            .fillMaxWidth()
            .size(150.dp)
    ) {
        TvLazyRow(
            state = scrollState,
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            items(imageList.size) { index ->
                val imageResId = imageList[index]
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = null, // No se necesita descripción para imágenes
                    modifier = Modifier
                        .fillMaxHeight() // Ocupa todo el espacio vertical
                        .size(300.dp)
                )
            }
        }
    }
}



@Composable
fun TransparentButton2(text: String) {
    Button(
        onClick = { /* Acción al hacer clic en el botón */ },
        modifier = Modifier
            .background(Color.Transparent) // Transparencia ligera
            .padding(2.dp)
            .clip(MaterialTheme.shapes.small) // Redondeado
    ) {
        Text(text = text, color = Color.White)
    }
}

fun openYouTubeApp(context: Context) {
    val youtubePackage = "com.google.android.youtube.tv"
    val launchIntent = context.packageManager.getLaunchIntentForPackage(youtubePackage)
    if (launchIntent != null) {
        context.startActivity(launchIntent)
    } else {
        // Manejar el caso en que la aplicación no esté instalada
        Toast.makeText(context, "YouTube no está instalada.", Toast.LENGTH_SHORT).show()
    }
}

fun openSpotifyApp(context: Context) {
    val spotifyPackage = "com.spotify.music"
    val launchIntent: Intent? = context.packageManager.getLaunchIntentForPackage(spotifyPackage)
    if (launchIntent != null) {
        context.startActivity(launchIntent)
    } else {
        // Manejar el caso en que la aplicación no esté instalada
        Toast.makeText(context, "Spotify no está instalada.", Toast.LENGTH_SHORT).show()
    }
}
fun openNetflixApp(context: Context) {
    val netflixPackage = "com.netflix.mediaclient"
    val launchIntent: Intent? = context.packageManager.getLaunchIntentForPackage(netflixPackage)
    if (launchIntent != null) {
        context.startActivity(launchIntent)
    } else {
        // Manejar el caso en que la aplicación no esté instalada
        Toast.makeText(context, "Netflix no está instalada.", Toast.LENGTH_SHORT).show()
    }
}
fun openGoogleApp(context: Context) {
    val googlePackage = "com.google.android.googlequicksearchbox"
    val launchIntent: Intent? = context.packageManager.getLaunchIntentForPackage(googlePackage)
    if (launchIntent != null) {
        context.startActivity(launchIntent)
    } else {
        // Manejar el caso en que la aplicación no esté instalada
        Toast.makeText(context, "Google no está instalada.", Toast.LENGTH_SHORT).show()
    }
}

// Función para obtener el recurso de imagen basado en el nombre de la aplicación
fun getAppImageResource(appName: String): Int {
    return when (appName) {
        "YouTube" -> R.drawable.youtube
        "Netflix" -> R.drawable.netflix
        "Prime Video" -> R.drawable.prime_video
        "Hulu" -> R.drawable.hulu
        "Disney+" -> R.drawable.disney_plus
        "Spotify" -> R.drawable.spotify
        "Twitch" -> R.drawable.twitch
        "ESPN" -> R.drawable.espn
        "Full" -> R.drawable.definicion
        "Play Store" -> R.drawable.google
        "Google" -> R.drawable.cromo
        else -> R.drawable.girar // Imagen por defecto si no se encuentra
    }
}
//Fuuncion para la musica
@Composable
fun MusicScreen(navController: NavController) {
    val context = LocalContext.current
    var currentSongIndex by remember { mutableStateOf(0) }
    var isPlaying by remember { mutableStateOf(false) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }
    var fondo =  FondoConDegradadoRadial(showImage = true) // No muestra la imagen en ScreenApps

    val songs = listOf(
        Song(R.drawable.fondo, R.raw.beautiful, "Beautiful Things"),
        Song(R.drawable.fondo, R.raw.no, "No que eras fan")
        // Song(R.drawable.austronauta, R.raw.musica3, "Song 3")
    )

    fun playSong() {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context, songs[currentSongIndex].audioResId)
        }
        mediaPlayer?.start()
        isPlaying = true
    }

    fun pauseSong() {
        mediaPlayer?.pause()
        isPlaying = false
    }

    fun nextSong() {
        mediaPlayer?.release()
        currentSongIndex = (currentSongIndex + 1) % songs.size
        mediaPlayer = MediaPlayer.create(context, songs[currentSongIndex].audioResId)
        playSong()
    }

    fun previousSong() {
        mediaPlayer?.release()
        currentSongIndex = if (currentSongIndex > 0) currentSongIndex - 1 else songs.size - 1
        mediaPlayer = MediaPlayer.create(context, songs[currentSongIndex].audioResId)
        playSong()
    }

    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer?.release()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = songs[currentSongIndex].backgroundResId),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = songs[currentSongIndex].title,
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                TransparentButton(onClick = { previousSong() }, iconResId = R.drawable.ic_previous)
                TransparentButton(
                    onClick = {
                        if (isPlaying) {
                            pauseSong()
                        } else {
                            playSong()
                        }
                    },
                    iconResId = if (isPlaying) R.drawable.baseline_pause_24 else R.drawable.baseline_play_arrow_24
                )
                TransparentButton(onClick = { nextSong() }, iconResId = R.drawable.ic_next)
            }
        }
    }

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {

        }
    }
}

@Composable
fun TransparentButton(onClick: () -> Unit, iconResId: Int, iconColor: Color = Color.White) {
    Button(
        onClick, Modifier
            .padding(8.dp)
            .background(Color.Transparent),
        colors = ButtonDefaults.run {
            return@run colors(
                Color.Transparent, // Establecer el color de fondo transparente
                contentColor = Color.White // Establecer el color del contenido del botón
            )
        }
    ) {
        Image(
            painter = painterResource(id = iconResId),
            contentDescription = null,
            colorFilter = ColorFilter.tint(iconColor),
            modifier = Modifier.size(40.dp)
        )
    }
}

data class Song(val backgroundResId: Int, val audioResId: Int, val title: String)

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navegacion() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController, startDestination = "RutaUno") {
        composable("RutaUno") { Portada(navController) }
        composable("RutaDos") { SmartTvScreen(navController = navController, context = context) }
        composable("music_screen") { MusicScreen(navController = navController) }
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