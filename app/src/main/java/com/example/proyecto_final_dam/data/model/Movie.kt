package com.example.proyecto_final_dam.data.model

data class Movie(
    val id: String,
    val coverURL: String,
    val tittle: String,
    val director: String,
    val rating: Float,
    val downloads: Int
){
    //para poder permitir convertir los datos de Firebase a este modelo creamos un constructor
    constructor() : this("","","","",0.0f,0)
}