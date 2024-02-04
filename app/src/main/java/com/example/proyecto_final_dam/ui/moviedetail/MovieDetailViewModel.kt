package com.example.proyecto_final_dam.ui.moviedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_dam.domain.repositories.MovieRepository
import com.example.proyecto_final_dam.data.repositories.Result
import com.example.proyecto_final_dam.domain.entities.MovieEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

//inyectamos nuestro repositorio usando la interfaz
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _movieState = MutableStateFlow<Result<MovieEntity>>(Result.Loading)
    val movieState = _movieState as StateFlow<Result<MovieEntity>>

    init {
        val movieId: String? = savedStateHandle["movieId"]
        if (movieId != null) {
            loadMovie(movieId)
        } else {
            // Establece un estado inicial que no sea de carga para permitir la adición de una nueva película.
            _movieState.value = Result.Success(MovieEntity("", "", "", "", 0f, 0))
        }
    }

    private fun loadMovie(movieId: String) {
        viewModelScope.launch {
            movieRepository.getMovieById(movieId).collect { result ->
                _movieState.value = result
            }
        }
    }

    fun addNewMovie(title: String, director: String, coverURL: String) {
        viewModelScope.launch {
            val newMovie = MovieEntity(UUID.randomUUID().toString(), coverURL, title, director, 0.0f, 0)//añadimos una nueva pelicula
            movieRepository.addMovie(newMovie)
        }
    }

    fun updateMovie(movie: MovieEntity) {
        viewModelScope.launch {
            movieRepository.updateMovie(movie).also {
                loadMovie(movie.id) // recarga los detalles de la película después de actualizar
            }
        }
    }
}