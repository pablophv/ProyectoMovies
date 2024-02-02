package com.example.proyecto_final_dam.ui.movieList

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_dam.data.repositories.MovieRepository
import com.example.proyecto_final_dam.data.repositories.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val movieRepository: MovieRepository//inyectamos el repositorio
) : ViewModel() {
    /*
    creamos una variable para controlar los estados de nuestro ui, creamos una data class para controlar
    los estados
     */
    private val _state: MutableState<MovieListState> = mutableStateOf(MovieListState())
    val state: State<MovieListState> = _state

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing


    //cuando cargue el view model se ejecutara la funcion que llama a la lista
    init {
        getMovieList()

    }

    //funcion para obtener lista de libros
    fun getMovieList() {

        movieRepository.getMovieList().onEach { result ->
            when (result) {
                is Result.Error -> {

                    _state.value = MovieListState(error = result.message ?: "Error inesperado")
                }

                is Result.Loading -> {
                    _state.value = MovieListState(isLoading = true)

                }

                is Result.Success -> {
                    _state.value = MovieListState(movies = result.data ?: emptyList())

                }
            }

        }.launchIn(viewModelScope)

    }
    fun deleteMovie(movieId: String) {
        movieRepository.deleteMovie(movieId)
        getMovieList()//actualiza la lista despues de borrar
    }
}