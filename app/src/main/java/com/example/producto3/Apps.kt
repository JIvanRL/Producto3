package com.example.producto3

import android.content.Context
import android.content.Intent
import android.widget.Toast

fun openYouTubeApp(context: Context) {
    val youtubePackage = "com.google.android.youtube.tv"  // Nombre del paquete correcto
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
fun openCrunchyrollApp(context: Context) {
    val crunchyrollPackage = "com.crunchyroll.crunchyroid"
    val launchIntent = context.packageManager.getLaunchIntentForPackage(crunchyrollPackage)
    if (launchIntent != null) {
        context.startActivity(launchIntent)
    } else {
        // Manejar el caso en que la aplicación no esté instalada
        Toast.makeText(context, "Crunchyroll no está instalada.", Toast.LENGTH_SHORT).show()
    }
}

fun openGoogleApp(context: Context) {
    val googlePackage = "com.google.android.apps.search.googleapp"
    val launchIntent: Intent? = context.packageManager.getLaunchIntentForPackage(googlePackage)
    if (launchIntent != null) {
        context.startActivity(launchIntent)
    } else {
        // Manejar el caso en que la aplicación no esté instalada
        Toast.makeText(context, "Google no está instalada.", Toast.LENGTH_SHORT).show()
    }
}
fun openPlayStoreApp(context: Context) {
    val playStorePackage = "com.android.vending"
    val launchIntent = context.packageManager.getLaunchIntentForPackage(playStorePackage)
    if (launchIntent != null) {
        context.startActivity(launchIntent)
    } else {
        // Manejar el caso en que la aplicación no esté instalada
        Toast.makeText(context, "Google Play Store no está instalada.", Toast.LENGTH_SHORT).show()
    }
}