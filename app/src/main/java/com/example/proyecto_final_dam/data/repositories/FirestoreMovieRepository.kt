package com.example.proyecto_final_dam.data.repositories

import com.example.proyecto_final_dam.data.model.Movie
import com.example.proyecto_final_dam.domain.entities.MovieEntity
import com.example.proyecto_final_dam.domain.mappers.toDataModel
import com.example.proyecto_final_dam.domain.mappers.toDomainModel
import com.example.proyecto_final_dam.domain.repositories.MovieRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

//en este archivo se proveen las instancias de la base de datos FireBase y de la lista de peliculas
@Singleton
class FirestoreMovieRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) : MovieRepository{
    //inyectamos la instancia de FireBase para poder llamar a la lista de peliculas
    override fun getMovies(): Flow<Result<List<MovieEntity>>> = flow {
        try {
            emit(Result.Loading)//emite un estado de carga
            val querySnapshot = firestore.collection("movies").get().await()
            val movies = querySnapshot.toObjects(Movie::class.java).map { it.toDomainModel() }
            emit(Result.Success(movies))
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }.catch { e ->
        emit(Result.Error(e.localizedMessage ?: "An error occurred"))
    }

    //metodo para añadir una pelicula
    override suspend fun addMovie(movie: MovieEntity): Result<Unit> {//
        return try {
            //añadadimos una pelicula a la lista
            firestore.collection("movies").document(movie.id).set(movie.toDataModel()).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An unknown error occurred")
        }
    }
    //metodo para actualizar una pelicula
    override suspend fun updateMovie(movie: MovieEntity): Result<Unit> {
        return try {
            val movieData = movie.toDataModel()//actualizamos una pelicula en la lista
            firestore.collection("movies").document(movie.id).set(movieData).await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An unknown error occurred")
        }
    }
    //metodo para eliminar una pelicula
    override suspend fun deleteMovie(movieId: String): Result<Unit> {
        return try {
            firestore.collection("movies").document(movieId).delete().await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e.localizedMessage ?: "An unknown error occurred")
        }
    }
    //metodo para obtener una pelicula por su id
    override fun getMovieById(movieId: String): Flow<Result<MovieEntity>> = flow {
        try {
            emit(Result.Loading)
            //obtenemos una pelicula por su id
            val documentSnapshot = firestore.collection("movies").document(movieId).get().await()
            val movie = documentSnapshot.toObject(Movie::class.java)?.toDomainModel()
            if (movie != null) {
                emit(Result.Success(movie))
            } else {
                emit(Result.Error("Movie not found"))
            }
        } catch (e: Exception) {
            emit(Result.Error(e.localizedMessage ?: "An unknown error occurred"))
        }
    }.catch { e ->//control de errores
        emit(Result.Error(e.localizedMessage ?: "An error occurred"))
    }
}