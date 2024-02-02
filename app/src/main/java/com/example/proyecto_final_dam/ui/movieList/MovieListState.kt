package com.example.proyecto_final_dam.ui.movieList

import com.example.proyecto_final_dam.data.model.Movie

data class MovieListState(
    val isLoading: Boolean = false,
    val movies: List<Movie>  = emptyList(),
    val error: String = ""
)