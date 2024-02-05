package com.example.proyecto_final_dam.domain.mappers

import com.example.proyecto_final_dam.data.model.DogImage
import com.example.proyecto_final_dam.domain.entities.DogImageEntity

//convertimos el modelo de datos de la imagen del perro a un modelo de dominio
fun DogImage.toDomain(): DogImageEntity {
    return DogImageEntity(
        imagenUrl = this.message ?: "",
    )
}