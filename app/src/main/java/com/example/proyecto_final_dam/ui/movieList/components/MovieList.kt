package com.example.proyecto_final_dam.ui.movieList.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.proyecto_final_dam.ui.movieList.MovieListState
import com.example.proyecto_final_dam.ui.theme.NearlyBlack
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun MovieList(
    modifier: Modifier,//adaptamos el contenido a la pantalla
    state: MovieListState,
    isRefreshing: Boolean,
    refreshData: () -> Unit,
    onItemClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
) {

    Box(
        modifier = modifier.fillMaxSize(),
    ) {
        //LAZY COLUMN
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing),
            /*
            con SwipeRefresh cuando el usuario desliza la pantalla para actualizarla se llama a la funcion refreshData
             */
            onRefresh = { refreshData() }
        ) {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(NearlyBlack)
            ) {
                items(
                    items = state.movies,
                    key = { movie -> movie.id }//utilizamos la funcion key para optimizar rendimiento, evita que se reconstruya toda la lista
                ) { movie ->
                    MovieListItem(
                        movie = movie,
                        onItemClick = onItemClick,
                        onDeleteClick = onDeleteClick
                    )
                }
            }
        }


        if (state.error?.isNotBlank() == true) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .align(Alignment.Center),
                text = state.error,
                color = Color.Red,
                textAlign = TextAlign.Center
            )
        }
        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }


    }
}


