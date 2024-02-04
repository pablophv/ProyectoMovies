package com.example.proyecto_final_dam.ui.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.navArgument

sealed class Destination(//clase sellada que nos permite manejar las rutas de la aplicacion
    val route: String,
    val arguments: List<NamedNavArgument>
){
    object MainScreen: Destination("mainScreen", emptyList())
    object MovieList: Destination("movieList", emptyList())
    object MovieDetail: Destination(
        route = "movieDetail",
        arguments = listOf(
            navArgument("movieId"){
                nullable = true
            }
        )
    )
}