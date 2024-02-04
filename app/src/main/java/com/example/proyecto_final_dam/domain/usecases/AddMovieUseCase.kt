package com.example.proyecto_final_dam.domain.usecases

import com.example.proyecto_final_dam.data.repositories.Result
import com.example.proyecto_final_dam.domain.entities.MovieEntity
import com.example.proyecto_final_dam.domain.repositories.MovieRepository
import javax.inject.Inject
//caso de uso que nos permite añadir una pelicula a la base de datos
class AddMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movie: MovieEntity): Result<Unit> {
        return movieRepository.addMovie(movie)
    }
}