package com.example.proyecto_final_dam.ui.moviedetail

import com.example.proyecto_final_dam.data.model.Movie

data class MovieDetailState (
    val isLoading: Boolean = false,
    val movie: Movie? = null,
    val error: String = ""
)