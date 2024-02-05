package com.example.proyecto_final_dam.domain.mappers

import com.example.proyecto_final_dam.data.model.Movie
import com.example.proyecto_final_dam.domain.entities.MovieEntity
/*
esta clase se encarga de mapear los datos de la base de datos a los
datos que se usan en la aplicacion
 */
//convertimos el modelo de datos de la pelicula a un modelo de dominio
fun Movie.toDomainModel(): MovieEntity {
    return MovieEntity(
        id = this.id,
        coverURL = this.coverURL,
        title = this.tittle,
        director = this.director,
        rating = this.rating,
        downloads = this.downloads
    )
}

fun MovieEntity.toDataModel(): Movie {
    return Movie(
        id = this.id,
        coverURL = this.coverURL,
        tittle = this.title,
        director = this.director,
        rating = this.rating,
        downloads = this.downloads
    )
}