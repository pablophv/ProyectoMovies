package com.example.proyecto_final_dam.app

import com.example.proyecto_final_dam.data.network.ApiService
import com.example.proyecto_final_dam.data.repositories.DogRepositoryImpl
import com.example.proyecto_final_dam.data.repositories.FirestoreMovieRepository
import com.example.proyecto_final_dam.domain.repositories.DogRepository
import com.example.proyecto_final_dam.domain.repositories.MovieRepository
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/*
 en este archivo se proveen las instancias de la base de datos FireBase y de la lista de peliculas
 tambien se proveen las instancias de retrofit y apiService
 */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    //proveemos la instancia para la base de datos FireBase
    @Provides
    @Singleton
    fun provideFirestoreInstance() = FirebaseFirestore.getInstance()

    //inyectamos la instancia de FireBase para poder llamar a la lista de libros
    @Provides
    @Singleton
    fun provideMovieList(
        firestore: FirebaseFirestore
    ): CollectionReference = firestore.collection("movies")

    @Provides
    @Singleton//inyectamos la instancia de la lista de peliculas en el repositorio
    fun provideMovieRepository(firestore: FirebaseFirestore): MovieRepository {
        return FirestoreMovieRepository(firestore)
    }

    @Provides
    @Singleton//crea y devuelve una instancia de retrofit
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://dog.ceo/api/") // Sustituye con la base URL de tu API
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton//recibe una instancia de retrofit como parametro, y devuelve una instancia de ApiService
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)


    @Singleton
    @Provides//inyectamos ApiService en el repository
    fun provideDogRepository(apiService: ApiService): DogRepository {
        return DogRepositoryImpl(apiService)
    }

}