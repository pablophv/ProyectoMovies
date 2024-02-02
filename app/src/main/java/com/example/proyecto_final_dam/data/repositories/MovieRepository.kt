package com.example.proyecto_final_dam.data.repositories

import com.example.proyecto_final_dam.data.model.Movie
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepository @Inject constructor(
    //la funcion que obtiene la lista de peliculas nos devuelve un CollectionReference
    //ahora ya podemos usar la lista de peliculas en cualquier parte de nuestro movieRepositori
    private val movieList: CollectionReference
) {

    fun addNewMovie(movie: Movie) {
        try {
            //creamos un nuevo documento, que sera cada uno de los registros que guardaremos en nuestra base de datos
            movieList.document(movie.id).set(movie)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    /*
    funcion para traer los datos desde firebase
    usamos Flow para emitir el resultado desde la base de datos
    como recibimos informacion desde firebase puede suceder que la informacion no llegue bien,
    o suceda un error por esto tenemos que controlar todos estos resultados y para eso creamos una
    sealed class llamada result donde controlaremos los resultados posibles
    */
    fun getMovieList(): Flow<Result<List<Movie>>> = flow {

        try {
            emit(Result.Loading<List<Movie>>())
            /*
            la variable movieList nos retorna un dato de tipo QuerySnapShot y lo convertimos
            a un tipo Lista
             */
            val movieList = movieList.get().await().map { document ->
                document.toObject(Movie::class.java)//creamos un objeto tipo pelicula
            }
            emit(Result.Success<List<Movie>>(data = movieList))
        } catch (e: Exception) {
            emit(Result.Error<List<Movie>>(message = e.localizedMessage ?: "Error desconocido"))
        }
    }

    fun getMovieById(movieId: String): Flow<Result<Movie>> = flow {

        try {

            emit(Result.Loading<Movie>())

            val movie = movieList
                .whereGreaterThanOrEqualTo("id", movieId)
                .get()
                .await()
                .toObjects(Movie::class.java)
                .first()
            emit(Result.Success<Movie>(data = movie))

        } catch (e: Exception) {

            emit(Result.Error<Movie>(message = e.localizedMessage ?: "Error desconocido"))

        }
    }

    fun updateMovie(movieId: String, movie: Movie){

        try {
            val map = mapOf(
                "tittle" to movie.tittle,
                "director" to movie.director,
                "coverURL" to movie.coverURL
            )

            movieList.document(movieId).update(map)
        }catch (e: Exception){
            e.printStackTrace()
        }

    }

    fun deleteMovie(movieId: String){

        try {
            movieList.document(movieId).delete()
        }catch (e: Exception){
            e.printStackTrace()
        }
    }
}