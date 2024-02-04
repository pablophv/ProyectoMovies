package com.example.proyecto_final_dam.domain.usecases

import com.example.proyecto_final_dam.data.repositories.Result
import com.example.proyecto_final_dam.domain.repositories.MovieRepository
import javax.inject.Inject
//caso de uso que nos permite eliminar una pelicula de la base de datos
class DeleteMovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend operator fun invoke(movieId: String): Result<Unit> {
        return movieRepository.deleteMovie(movieId)
    }
}