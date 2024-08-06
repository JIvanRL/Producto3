package com.example.producto3

import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
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

@Composable
fun SmartTvScreen(navController: NavController) {
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
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

            AutoScrollingCarousel(movieList = movieList, navController = navController)

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(movieList.size) { index ->
                        val movie = movieList[index]
                        Image(
                            painter = painterResource(id = movie.imageResId),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxHeight()
                                .size(120.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(appList.size) { index ->
                        val app = appList[index]
                        AppListItem(appName = app) { selectedApp ->
                            when (selectedApp) {
                                "YouTube" -> openYouTubeApp(context)
                                "Spotify" -> navController.navigate("music_screen")
                                "Netflix" -> openCrunchyrollApp(context)
                                "Google" -> openGoogleApp(context)
                                "Play Store" -> openPlayStoreApp(context)
                                "Hulu" -> openSpotifyApp(context)
                                else -> navController.navigate("detail_screen/$selectedApp")
                            }
                        }
                    }
                }
            }
        }
    }
}


//Carrusel
@Composable
fun AutoScrollingCarousel(movieList: List<Movie>, navController: NavController) {
    val scrollState = rememberLazyListState()
    val scrollInterval = 3000L
    val scrollAmount = 1

    LaunchedEffect(Unit) {
        while (true) {
            delay(scrollInterval)
            val nextItemIndex = (scrollState.firstVisibleItemIndex + scrollAmount) % movieList.size
            scrollState.animateScrollToItem(nextItemIndex)
        }
    }

    Box(
        modifier = Modifier
            .background(Color.Transparent)
            .fillMaxWidth()
            .size(150.dp)
    ) {
        LazyRow(
            state = scrollState,
            horizontalArrangement = Arrangement.spacedBy(30.dp)
        ) {
            items(movieList.size) { index ->
                val movie = movieList[index]
                Image(
                    painter = painterResource(id = movie.imageResId),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxHeight()
                        .size(300.dp)
                        .clickable {
                            navController.navigate("Description/${movie.title}")
                        }
                )
            }
        }
    }
}

//Pantalla para el visto de descripccion
@Composable
fun MovieDescriptionScreen(navController: NavController, movieTitle: String?) {
    val movie = movieList.find { it.title == movieTitle }

    movie?.let {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Black)
        ) {
            // Muestra el video
            VideoPlayer(url = movie.videoUrl)

            // Muestra la imagen con un gradiente
            Image(
                painter = painterResource(id = movie.imageResId),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(colors = listOf(Color.Transparent, Black), startY = 300f))
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
                    .align(Alignment.BottomStart)
            ) {
                Spacer(modifier = Modifier.height(300.dp)) // Espacio para mostrar la imagen antes del contenido
                Text(
                    text = movie.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = "Fecha de lanzamiento: ${movie.releaseDate}",
                    fontSize = 16.sp,
                    color = Color.White,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Text(
                    text = movie.description,
                    fontSize = 16.sp,
                    color = Color.White
                )
            }
        }
    } ?: run {
        // Manejar el caso donde la película no se encuentra
        Text(
            text = "Película no encontrada",
            color = Color.White,
            modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
        )
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
data class Song(val backgroundResId: Int, val audioResId: Int, val title: String)
@Composable
fun MusicScreen(navController: NavController) {
    val context = LocalContext.current
    var currentSongIndex by remember { mutableStateOf(0) }
    var isPlaying by remember { mutableStateOf(false) }
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    val songs = listOf(
        Song(R.drawable.fondo, R.raw.beautiful, "Beautiful Things"),
        Song(R.drawable.fondo, R.raw.no, "No que eras fan"),
        Song(R.drawable.fondo, R.raw.pain2, "Pain- Jhos Apa"),
        Song(R.drawable.fondo, R.raw.hakuna, "DAAZ - HAKUNA MATATA (feat. Homievaldes, Fano) (prod. Meny Mendez) (Video Oficial)")
        // Agrega más canciones aquí si lo deseas
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
            .background(Black)
    ) {
        // Imagen de fondo de la canción actual
        Image(
            painter = painterResource(id = songs[currentSongIndex].backgroundResId),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f) // Opcional: reducir la opacidad de la imagen de fondo
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Título de la canción actual
            Text(
                text = songs[currentSongIndex].title,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 16.dp, bottom = 32.dp)
            )

            // Controles de reproducción
            Spacer(modifier = Modifier.weight(1f)) // Espacio flexible para centrar los controles

            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier.fillMaxWidth()
            ) {
                IconButton(onClick = { previousSong() }) {
                    Icon(painterResource(id = R.drawable.ic_previous), contentDescription = "Previous", tint = Color.White)
                }
                IconButton(onClick = {
                    if (isPlaying) {
                        pauseSong()
                    } else {
                        playSong()
                    }
                }) {
                    Icon(
                        painterResource(id = if (isPlaying) R.drawable.baseline_pause_24 else R.drawable.baseline_play_arrow_24),
                        contentDescription = if (isPlaying) "Pause" else "Play",
                        tint = Color.White
                    )
                }
                IconButton(onClick = { nextSong() }) {
                    Icon(painterResource(id = R.drawable.ic_next), contentDescription = "Next", tint = Color.White)
                }
            }

            // Lista de canciones
            Spacer(modifier = Modifier.height(32.dp)) // Espacio entre controles y lista de canciones

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(songs) { song ->
                    SongItem(song = song, onClick = {
                        currentSongIndex = songs.indexOf(song)
                        playSong()
                    })
                }
            }
        }
    }
}

@Composable
fun SongItem(song: Song, onClick: () -> Unit) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
            .background(Color.Gray.copy(alpha = 0.3f)) // Color de fondo para la lista de canciones
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

            Image(
                painter = painterResource(id = song.backgroundResId),
                contentDescription = null,
                modifier = Modifier.size(50.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = song.title,
                color = Color.White,
                fontSize = 16.sp
            )

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