package com.example.proyecto_final_dam.ui.movieList.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.proyecto_final_dam.domain.entities.MovieEntity

@Composable
fun MovieListItem(
    movie: MovieEntity,
    onItemClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {//esta funcion nos permitira mostrar los datos de la pelicula
    Box(
        modifier = Modifier
            .fillMaxWidth(),
    ) {
        // elemento que muestra los datos de la película
        Card(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxSize()
                .height(150.dp)
                .clickable { onItemClick(movie.id) },
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = movie.coverURL),
                    contentDescription = "Movie Cover",
                    modifier = Modifier
                        .fillMaxHeight()
                        .size(120.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.width(25.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = movie.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "Director: ${movie.director}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(Modifier.height(30.dp))

                    Row {
                       Text(text = "'Pulsa para modicar'", fontSize = 15.sp, color = Color.Gray, modifier = Modifier.padding(bottom = 10.dp))
                        Spacer(Modifier.width(60.dp))
                        IconButton(onClick = { onDeleteClick(movie.id) },modifier = Modifier.size(60.dp)) {
                            Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }
    }
}
