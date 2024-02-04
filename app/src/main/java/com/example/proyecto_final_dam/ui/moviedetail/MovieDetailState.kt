package com.example.proyecto_final_dam.ui.moviedetail

import com.example.proyecto_final_dam.domain.entities.MovieEntity
//control de estados de la pantalla de detalle de la pelicula
data class MovieDetailState (
    val isLoading: Boolean = false,
    val movie: MovieEntity? = null,
    val error: String = ""
)

