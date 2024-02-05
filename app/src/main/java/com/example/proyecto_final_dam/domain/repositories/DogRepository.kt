package com.example.proyecto_final_dam.domain.repositories

import com.example.proyecto_final_dam.domain.entities.DogImageEntity

interface DogRepository {//definimos la interfaz del repositorio
    suspend fun getDogImage(): DogImageEntity
}