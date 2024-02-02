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
import com.example.proyecto_final_dam.data.model.Movie

@Composable
fun MovieListItem(
    movie: Movie,
    onItemClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
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
                    modifier = Modifier.fillMaxHeight()
                        .size(120.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.width(25.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = movie.tittle,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Director: ${movie.director}",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    // Omitido: RatingBar y otros elementos, reemplaza según tu implementación

                    Spacer(Modifier.height(8.dp))

                    Row {
                        Button(
                            onClick = { /* acción de descarga */ },
                            modifier = Modifier.padding(end = 8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Cyan)
                        ) {
                            Text("Descargar")
                        }

                        IconButton(onClick = { onDeleteClick(movie.id) }) {
                            Icon(Icons.Filled.Delete, contentDescription = "Eliminar")
                        }
                    }
                }
            }
        }
    }
}