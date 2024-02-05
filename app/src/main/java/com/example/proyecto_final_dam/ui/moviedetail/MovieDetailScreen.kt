package com.example.proyecto_final_dam.ui.moviedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.proyecto_final_dam.R
import com.example.proyecto_final_dam.domain.entities.MovieEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    state: MovieDetailState,//con el state podremos controlar los estados de la ui
    addNewMovie: (String, String, String) -> Unit,
    updateMovie: (MovieEntity) -> Unit
) {

    var title by remember(state.movie?.title) {
        mutableStateOf(
            state.movie?.title ?: ""
        )
    }//variable para el titulo que escriba el usuario
    var director by remember(state.movie?.director) {
        mutableStateOf(
            state.movie?.director ?: ""
        )
    }//variable para el director que escriba el usuario
    var coverURL by remember(state.movie?.coverURL) {
        mutableStateOf(
            state.movie?.coverURL ?: ""
        )
    }//variable para la URL que escriba el usuario

    Box(//este box nos permitira mostrar los datos de la pelicula
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = title,
                onValueChange = { title = it },
                label = {
                    Text(text = "Title")
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = director,
                onValueChange = { director = it },
                label = {
                    Text(text = "Director")
                }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                value = coverURL,
                onValueChange = { coverURL = it },
                label = {
                    Text(text = "Image URL")
                }
            )


             Image(
                painterResource(id = R.drawable.portadas),
                contentDescription = "",
                modifier = Modifier
                    .size(350.dp),
                contentScale = ContentScale.Crop


            )


        }


            if (state.error.isNotBlank()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    text = state.error,
                    style = TextStyle(
                        color = Color.Red,
                        textAlign = TextAlign.Center
                    )
                )
            }
            val isAddingMovie = state.movie?.id.isNullOrEmpty()
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
                if (isAddingMovie) {
                    // Muestra el botón para añadir una nueva película
                    Button(
                        onClick = { addNewMovie(title, director, coverURL) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),
                        // Configuración del botón
                    ) {
                        Text("Add New Movie")
                    }
                } else {

                    // Muestra el botón para actualizar la película existente
                    Button(
                        onClick = {
                            updateMovie(
                                MovieEntity(
                                    id = state.movie?.id ?: "",
                                    title = title,
                                    director = director,
                                    coverURL = coverURL,
                                    rating = state.movie?.rating ?: 0f,
                                    downloads = state.movie?.downloads ?: 0
                                )
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomCenter),


                    ) {
                        Text("Update Movie")
                    }
                }
            }





    }
}