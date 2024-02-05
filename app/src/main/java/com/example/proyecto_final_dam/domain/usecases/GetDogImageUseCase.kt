package com.example.proyecto_final_dam.domain.usecases

import com.example.proyecto_final_dam.domain.entities.DogImageEntity
import com.example.proyecto_final_dam.domain.repositories.DogRepository
import javax.inject.Inject

//caso de uso para obtener la imagen aleatoria de un perro
class GetDogImageUseCase @Inject constructor(
    private val repository: DogRepository
) {
    suspend operator fun invoke(): DogImageEntity = repository.getDogImage()
}