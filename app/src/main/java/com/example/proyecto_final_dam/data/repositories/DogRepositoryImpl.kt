package com.example.proyecto_final_dam.data.repositories


import com.example.proyecto_final_dam.data.network.ApiService
import com.example.proyecto_final_dam.domain.entities.DogImageEntity
import com.example.proyecto_final_dam.domain.mappers.toDomain
import com.example.proyecto_final_dam.domain.repositories.DogRepository
import javax.inject.Inject

class DogRepositoryImpl @Inject constructor(//inyectamos ApiService en el repository
    private val apiService: ApiService
): DogRepository {
    override suspend fun getDogImage(): DogImageEntity {
        val response = apiService.getDogImage()
        if (response.isSuccessful && response.body() != null) {
            return response.body()!!.toDomain()
        } else {
            throw Exception("Error fetching dog image: ${response.errorBody()?.string() ?: "Unknown error"}")
        }
    }
}