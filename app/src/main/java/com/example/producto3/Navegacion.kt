package com.example.producto3

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navegacion() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "RutaUno") {
        composable("RutaUno") { Portada(navController) }
        composable("RutaDos") { SmartTvScreen(navController = navController) }
        composable("music_screen") { MusicScreen(navController = navController) }
        composable(
            "Description/{movieTitle}",
            arguments = listOf(navArgument("movieTitle") { type = NavType.StringType })
        ) { backStackEntry ->
            val movieTitle = backStackEntry.arguments?.getString("movieTitle")
            MovieDescriptionScreen(navController = navController, movieTitle = movieTitle)
        }
    }
}

