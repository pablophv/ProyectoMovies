package com.example.proyecto_final_dam.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyecto_final_dam.data.repositories.Result
import com.example.proyecto_final_dam.ui.movieList.MovieListScreen
import com.example.proyecto_final_dam.ui.movieList.MovieListViewModel
import com.example.proyecto_final_dam.ui.moviedetail.MovieDetailScreen
import com.example.proyecto_final_dam.ui.moviedetail.MovieDetailState
import com.example.proyecto_final_dam.ui.moviedetail.MovieDetailViewModel
import com.example.proyecto_final_dam.ui.navigation.Destination
import com.example.proyecto_final_dam.ui.theme.Proyecto_Final_DamTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Proyecto_Final_DamTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                     val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Destination.MainScreen.route,
                    ){
                        addStartScreen(navController)
                        addMovieList(navController)
                        addMovieDetail()
                    }

                }
            }
        }
    }
}
fun NavGraphBuilder.addStartScreen(navController: NavController) {
    composable(route = Destination.MainScreen.route) {
        MainScreen(navController = navController)
    }
}
fun NavGraphBuilder.addMovieList(
    navController: NavController
){
    composable(
        route = Destination.MovieList.route
    ){

        val viewModel: MovieListViewModel = hiltViewModel()
        //val state = viewModel.state.value
        val state = viewModel.moviesState.collectAsState().value
        val isRefreshing = viewModel.isRefreshing.collectAsState()

        MovieListScreen(
            state = state,
            navigateToMovieDetail = {
                navController.navigate(Destination.MovieDetail.route)
            },
            isRefreshing = isRefreshing.value,
            refreshData = {viewModel.loadMovies()},
            onItemClick = {movieId ->
                navController.navigate(
                    Destination.MovieDetail.route + "?movieId=$movieId"
                )
            },
            onDeleteClick = viewModel::deleteMovie,
            dogImageViewModel = hiltViewModel()
        )
    }
}
fun NavGraphBuilder.addMovieDetail() {
    composable(
        route = Destination.MovieDetail.route + "?movieId={movieId}"
    ){
        //creamos una instancia del viewModel
        val viewModel: MovieDetailViewModel = hiltViewModel()

        // Transforma Result<MovieEntity> a MovieDetailState
        val state = when (val result = viewModel.movieState.collectAsState().value) { // Result<MovieEntity>
            is Result.Loading -> MovieDetailState(isLoading = true)
            is Result.Success -> MovieDetailState(movie = result.data)
            is Result.Error -> MovieDetailState(error = result.message)
        }
        MovieDetailScreen(
            state = state,
            addNewMovie =viewModel::addNewMovie ,
            updateMovie = viewModel::updateMovie
        )
    }
}

