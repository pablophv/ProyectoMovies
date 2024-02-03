package com.example.proyecto_final_dam.ui.moviedetail

import com.example.proyecto_final_dam.domain.entities.MovieEntity

data class MovieDetailState (
    val isLoading: Boolean = false,
    val movie: MovieEntity? = null,
    val error: String = ""
)

