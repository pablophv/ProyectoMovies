package com.example.proyecto_final_dam.data.repositories

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null
){

    class Success<T>(data: T?) : Result<T>(data)//resultado correcto
    class Error<T>(message:String?,data: T? = null) : Result<T>(data,message)//resultado erróneo
    class Loading<T>(data: T? = null) : Result<T>(data)//cargando información
}