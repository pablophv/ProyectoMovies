package com.example.proyecto_final_dam.ui.movieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_dam.data.repositories.Result
import com.example.proyecto_final_dam.domain.entities.MovieEntity
import com.example.proyecto_final_dam.domain.repositories.MovieRepository
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
    private val movieRepository: MovieRepository // usamos la interfaz para la inyección de dependencias
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
            movieRepository.getMovies().collect { result ->
                _moviesState.value = result
            }
        }
    }

    fun deleteMovie(movieId: String) {
        viewModelScope.launch {
            movieRepository.deleteMovie(movieId).also {
                loadMovies() // Recarga la lista después de borrar
            }
        }
    }
}