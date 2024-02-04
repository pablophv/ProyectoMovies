package com.example.proyecto_final_dam.ui.movieList

import com.example.proyecto_final_dam.domain.entities.MovieEntity
//control de estados de la lista de peliculas
data class MovieListState(
    val isLoading: Boolean = false,
    val movies: List<MovieEntity>  = emptyList(),
    val error: String? = null
)


