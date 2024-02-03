package com.example.proyecto_final_dam.domain.entities

data class MovieEntity(
    val id: String,
    val coverURL: String,
    val title: String,
    val director: String,
    val rating: Float,
    val downloads: Int
)