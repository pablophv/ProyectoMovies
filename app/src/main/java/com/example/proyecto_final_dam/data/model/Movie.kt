package com.example.proyecto_final_dam.data.model
/*
modelo de datos de la pelicula
 */
data class Movie(
    val id: String,
    val coverURL: String,
    val tittle: String,
    val director: String,
    val rating: Float,
    val downloads: Int
){
    //constructor vacio
    constructor() : this("","","","",0.0f,0)
}