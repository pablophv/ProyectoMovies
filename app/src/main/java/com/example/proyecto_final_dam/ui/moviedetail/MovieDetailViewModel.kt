package com.example.proyecto_final_dam.ui.moviedetail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_dam.data.model.Movie
import com.example.proyecto_final_dam.data.repositories.MovieRepository
import com.example.proyecto_final_dam.data.repositories.Result
import com.example.proyecto_final_dam.ui.movieList.MovieListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.UUID
import javax.inject.Inject

//inyectamos nuestro repositorio
@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,//inyectamos el repositorio
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    //creamos una variable para controlar los estados de nuestro ui
    private val _state: MutableState<MovieDetailState> = mutableStateOf(MovieDetailState())
    val state: State<MovieDetailState>
        get() = _state

    init {
        /*
        recibimos el id de la pelicula
          */
        savedStateHandle.get<String>("movieId")?.let { movieId ->
            getMovie(movieId)
        }
    }

    fun addNewMovie(tittle: String, director: String,coverURL:String) {
        val movie = Movie(
            id = UUID.randomUUID().toString(),
            coverURL = coverURL,
            tittle = tittle,
            director = director,
            rating = 0.0f,
            downloads = 0
        )

        //llamamos al repositorio
        movieRepository.addNewMovie(movie)

    }

    private fun getMovie(movieId: String) {

        movieRepository.getMovieById(movieId).onEach { result ->
            when(result){

                is Result.Error -> {

                    _state.value = MovieDetailState(error = result.message ?: "Error inesperado")
                }

                is Result.Loading -> {
                    _state.value = MovieDetailState(isLoading = true)

                }

                is Result.Success -> {
                    _state.value = MovieDetailState(movie = result.data)

                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

    fun updateMovie(newTitle: String, newDirector: String, newCoverURL: String){
        if (state.value.movie == null){ // salta error si la pelicula no fuera nulo
            _state.value = MovieDetailState(error = "Movie is null")
            return
        }

        val movieEdited = state.value.movie!!.copy(
            tittle = newTitle,
            director = newDirector,
            coverURL = newCoverURL

        )

        movieRepository.updateMovie(movieEdited.id, movieEdited)
    }

}