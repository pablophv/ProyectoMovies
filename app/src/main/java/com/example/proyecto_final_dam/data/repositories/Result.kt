package com.example.proyecto_final_dam.data.repositories
//clase sellada que nos permite manejar los estados de la lista de peliculas
sealed class Result<out T> {
    object Loading : Result<Nothing>()
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
}