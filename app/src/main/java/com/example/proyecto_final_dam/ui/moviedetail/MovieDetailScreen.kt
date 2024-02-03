package com.example.proyecto_final_dam.ui.moviedetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyecto_final_dam.R
import com.example.proyecto_final_dam.domain.entities.MovieEntity
import com.example.proyecto_final_dam.ui.theme.Red100
import kotlin.reflect.KFunction1

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

    Box(
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
                    Text(text = "Url Imagen")
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.fillMaxWidth()) {

                Image(
                    painterResource(id = R.drawable.portadas),
                    contentDescription = "",
                    modifier = Modifier
                        .size(350.dp),
                    contentScale = ContentScale.Crop


                )
            }

        }

        if (state.error.isNotBlank()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                text = state.error,
            )
        }
//
        Spacer(modifier = Modifier.height(10.dp))
        if (state.movie?.id != null) {// si la pelicula que obtenemos es nulo creamos una pelicula nueva

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                onClick = {
                    //funcion para modificar una pelicula
                    updateMovie(MovieEntity(state.movie.id, coverURL, title, director, 0.0f, 0))

                },
                /*
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Red100
                )
                 */

            ) {
                Text(
                    text = "Update Movie",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }

        if (state.movie?.id == null) {// si la pelicula que obtenemos es nulo creamos una pelicula nueva
            Button(//si es nulo modificamos el libro seleccionado
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp)
                    .align(Alignment.BottomCenter),
                onClick = {
                    //funcion para agregar nuevos libros
                    addNewMovie(title, director, coverURL)
                },

                colors = ButtonDefaults.buttonColors(
                    contentColor = Red100
                )


            ) {
                Text(
                    text = "Add New Movie",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }

    }
}