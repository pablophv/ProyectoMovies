package com.example.proyecto_final_dam.data.network


import com.example.proyecto_final_dam.data.model.DogImage
import retrofit2.Response
import retrofit2.http.GET
//interfaz que define el metodo para obtener la imagen aleatoria de un perro desde la API
interface ApiService {
    @GET("breeds/image/random")
    suspend fun getDogImage(): Response<DogImage>
}