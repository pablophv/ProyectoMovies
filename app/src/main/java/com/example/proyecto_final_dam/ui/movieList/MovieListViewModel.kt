package com.example.proyecto_final_dam.ui.movieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_dam.data.repositories.Result
import com.example.proyecto_final_dam.domain.entities.MovieEntity
import com.example.proyecto_final_dam.domain.usecases.DeleteMovieUseCase
import com.example.proyecto_final_dam.domain.usecases.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val deleteMovieUseCase: DeleteMovieUseCase, // inyectamos el caso de uso para eliminar una película
    private val getMoviesUseCase: GetMoviesUseCase, // inyectamos el caso de uso para obtener todas las películas
) : ViewModel() {

    private val _moviesState = MutableStateFlow<Result<List<MovieEntity>>>(Result.Loading)

    val moviesState = _moviesState.map { result ->
        when (result) {
            is Result.Loading -> MovieListState(isLoading = true)
            is Result.Success -> MovieListState(movies = result.data)
            is Result.Error -> MovieListState(error = result.message)
        }
    }.stateIn(

        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = MovieListState(isLoading = true)
    )
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing
    init {
        loadMovies()

    }

    fun loadMovies() {
        viewModelScope.launch {
            getMoviesUseCase().collect { result ->//usamos el caso de uso para obtener todas las películas
                _moviesState.value = result
            }
        }
    }
    fun deleteMovie(movieId: String) {
        viewModelScope.launch {
            //usamos el caso de uso para eliminar la película
            deleteMovieUseCase(movieId).also {
                // Recargamos la lista de películas después de la eliminación
                loadMovies()
            }
        }
    }
}