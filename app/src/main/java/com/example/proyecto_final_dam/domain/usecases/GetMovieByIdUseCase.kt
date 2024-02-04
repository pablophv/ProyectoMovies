package com.example.proyecto_final_dam.domain.usecases

import com.example.proyecto_final_dam.data.repositories.Result
import com.example.proyecto_final_dam.domain.entities.MovieEntity
import com.example.proyecto_final_dam.domain.repositories.MovieRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetMovieByIdUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    operator fun invoke(movieId: String): Flow<Result<MovieEntity>> {
        return movieRepository.getMovieById(movieId)
    }
}