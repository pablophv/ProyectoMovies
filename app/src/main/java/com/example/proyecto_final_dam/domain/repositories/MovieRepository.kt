package com.example.proyecto_final_dam.domain.repositories

import com.example.proyecto_final_dam.data.repositories.Result
import com.example.proyecto_final_dam.domain.entities.MovieEntity
import kotlinx.coroutines.flow.Flow
//interfaz que nos permite manejar las operaciones de la base de datos
interface MovieRepository {

    fun getMovies(): Flow<Result<List<MovieEntity>>>
    suspend fun addMovie(movie: MovieEntity): Result<Unit>
    suspend fun updateMovie(movie: MovieEntity): Result<Unit>
    suspend fun deleteMovie(movieId: String): Result<Unit>
    fun getMovieById(movieId: String): Flow<Result<MovieEntity>>


}