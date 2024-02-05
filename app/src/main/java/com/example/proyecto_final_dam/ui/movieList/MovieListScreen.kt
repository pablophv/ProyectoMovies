package com.example.proyecto_final_dam.ui.movieList

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.More
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.VideoCameraBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.example.proyecto_final_dam.ui.dogImage.DogImageViewModel
import com.example.proyecto_final_dam.ui.movieList.components.MovieList
import com.example.proyecto_final_dam.ui.theme.NearlyBlack
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun MovieListScreen(
    dogImageViewModel: DogImageViewModel,//viewModel para obtener la imagen aleatoria de un perro
    state: MovieListState,//estado de la lista de peliculas
    navigateToMovieDetail: () -> Unit,//navegar a la pantalla de detalle de la pelicula
    isRefreshing: Boolean,
    refreshData: () -> Unit,
    onItemClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)//estado del drawer
    val scope = rememberCoroutineScope()//scope para el drawer

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {DrawerContent(dogImageViewModel)},
    ) {

        Scaffold(
            modifier = Modifier.fillMaxSize(),

            topBar = { MyTopAppBarCenter(drawerState = drawerState) },
            bottomBar = {
                MyBottomAppBar(
                    onOpenDrawer = { scope.launch { drawerState.open() } }
                )
            },
            floatingActionButton = {
                MyFloatingActionButton(onClick = navigateToMovieDetail)
            },
        ) {innerPadding ->//innerPadding nos permite adaptar el contenido a la pantalla
            MovieList(
                state = state,
                isRefreshing = isRefreshing,
                refreshData = refreshData,
                onItemClick = onItemClick,
                onDeleteClick = onDeleteClick,
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}
@Composable
fun MyBottomAppBar(onOpenDrawer: () -> Unit) {
    BottomAppBar(
        modifier = Modifier.height(50.dp),
        containerColor = Color.Red,
        actions = {
            Row (horizontalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxWidth()){
                IconButton(onClick = onOpenDrawer) {
                    Icon(
                        imageVector = Icons.Default.CameraAlt, // icono para abrir el drawer
                        contentDescription = ""
                    )
                }

                IconButton(onClick = onOpenDrawer) {
                    Icon(
                        imageVector = Icons.Default.Favorite, // icono para abrir el drawer
                        contentDescription = ""
                    )
                }

                IconButton(onClick = onOpenDrawer) {
                    Icon(
                        imageVector = Icons.Default.VideoCameraBack, // icono para abrir el drawer
                        contentDescription = "Open Drawer"
                    )
                }

                IconButton(onClick = onOpenDrawer) {
                    Icon(
                        imageVector = Icons.Default.More, // icono para abrir el drawer
                        contentDescription = "Open Drawer"
                    )
                }
            }

        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerContent(dogImageViewModel: DogImageViewModel) {
    val imageUrl = dogImageViewModel.imageUrl.collectAsState().value
    ModalDrawerSheet(
        drawerContainerColor = NearlyBlack,
        content = {

            imageUrl?.let { url ->
                Image(//mostramos la imagen aleatoria de un perro desde la url
                    painter = rememberAsyncImagePainter(url),
                    contentDescription = "Imagen aleatoria de un perro",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentScale = ContentScale.FillWidth
                )
            } ?: run {
                Text("Cargando imagen...", modifier = Modifier.padding(16.dp))
            }

            Spacer(Modifier.height(16.dp))
            Column (modifier = Modifier.padding(16.dp)){
                Button(onClick = {},modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    Icon(Icons.Filled.Home, contentDescription = "Inicio")
                    Text(text = "Inicio")
                }
                Button(onClick = {},modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    Icon(Icons.Filled.AccountBox, contentDescription = "Cuenta")
                    Text(text = "Cuenta")
                }
                Button(onClick = {},modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    Icon(Icons.Filled.Favorite, contentDescription = "favoritos")
                    Text(text = "Peliculas Favoritas")
                }
                Button(onClick = {},modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)) {
                    Icon(Icons.Filled.Settings, contentDescription = "Ajustes")
                    Text(text = "Ajustes")
                }
            }


        }
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBarCenter(drawerState: DrawerState) {
    val scope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Save your movies", fontSize = 20.sp)
            }
        },
        navigationIcon = {
            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            IconButton(onClick = { }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "more options")
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color.Red
        )
    )
}

@Composable
fun MyFloatingActionButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        containerColor = Color.Red,
        contentColor = Color.White,
        shape = CircleShape
    ) {
        Icon(imageVector = Icons.Default.Add, contentDescription = "Add Icon")
    }
}



