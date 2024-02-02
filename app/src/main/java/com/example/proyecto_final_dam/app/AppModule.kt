package com.example.proyecto_final_dam.app

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

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
    fun provideBookList(
        firestore: FirebaseFirestore
    ) =  firestore.collection("movies")

}