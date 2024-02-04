package com.example.proyecto_final_dam.domain.usecases

import com.example.proyecto_final_dam.data.repositories.Result
import com.example.proyecto_final_dam.domain.entities.MovieEntity
import com.example.proyecto_final_dam.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
//caso de uso que nos permite obtener todas las peliculas de la base de datos
class GetMoviesUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(): Flow<Result<List<MovieEntity>>> {
        return movieRepository.getMovies()
    }
}