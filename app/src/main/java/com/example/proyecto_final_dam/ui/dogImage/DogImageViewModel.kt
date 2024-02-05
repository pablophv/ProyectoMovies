package com.example.proyecto_final_dam.ui.dogImage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyecto_final_dam.domain.usecases.GetDogImageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DogImageViewModel @Inject constructor(
    private val getDogImageUseCase: GetDogImageUseCase//caso de uso para obtener la imagen aleatoria de un perro
) : ViewModel() {

    private val _imageUrl = MutableStateFlow<String?>(null)
    val imageUrl: StateFlow<String?> = _imageUrl.asStateFlow()

    init {
        loadDogImage()
    }
    //carga la imagen del perro
    private fun loadDogImage() {
        viewModelScope.launch {
            try {
                val dogImage = getDogImageUseCase()
                _imageUrl.value = dogImage.imagenUrl
            } catch (e: Exception) {
                _imageUrl.value = null // Considera proporcionar un feedback al usuario
            }
        }
    }
}