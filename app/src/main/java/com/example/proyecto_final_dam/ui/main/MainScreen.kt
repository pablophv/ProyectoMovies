package com.example.proyecto_final_dam.ui.main

import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.InfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyecto_final_dam.ui.navigation.Destination
import com.example.proyecto_final_dam.ui.theme.CouretteRegular
import kotlinx.coroutines.delay


@Composable
fun MainScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            fontSize = 30.sp,
            text = "Bienvenido!!!",
            fontWeight = FontWeight.ExtraBold,
            fontFamily = CouretteRegular,
            modifier = Modifier.padding( 10.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        AnimacionCanvas()

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            fontSize = 25.sp,
            text = "Organiza tus peliculas favoritas",
            fontWeight = FontWeight.ExtraBold,
            fontFamily = CouretteRegular,
            modifier = Modifier.padding( 10.dp)
        )

        // navegación automática a MovieList después de 4 segundos
        LaunchedEffect(Unit) {
            delay(4000) // espera 7 segundos
            navController.navigate(Destination.MovieList.route) {
                //evitar volver a la pantalla de bienvenida al presionar atrás
                popUpTo(Destination.MainScreen.route) { inclusive = true }
            }
        }
    }
}

@Composable
fun AnimacionCanvas() {
    val transition: InfiniteTransition = rememberInfiniteTransition(label = "")
    val radioAnimado by transition.animateFloat(
        initialValue = 50f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                delayMillis = 100,
                FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val anguloAnimado1 by transition.animateFloat(
        initialValue = 0f,
        targetValue = 180f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                delayMillis = 100,
                FastOutLinearInEasing
            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )

    val anguloAnimado2 by transition.animateFloat(
        initialValue = 180f,
        targetValue = 300f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                delayMillis = 100,
                FastOutLinearInEasing

            ),
            repeatMode = RepeatMode.Reverse
        ), label = ""
    )
    Box(
        modifier = Modifier
            .fillMaxWidth(),
        contentAlignment = Alignment.Center

    ) {

        Canvas(
            modifier = Modifier
                .padding(12.dp)
                .size(300.dp)
        ) {

            drawArc(
                color = Color.Blue,
                startAngle = anguloAnimado1,
                sweepAngle = 90f,
                useCenter = false,
                style = Stroke(
                    width = 50f,
                    cap = StrokeCap.Round
                )
            )

            drawArc(
                color = Color.Blue,
                startAngle = anguloAnimado2,
                sweepAngle = 90f,
                useCenter = false,
                style = Stroke(
                    width = 50f,
                    cap = StrokeCap.Round
                )
            )
            drawCircle(
                color = Color.Red,
                radius = 320f
            )

            drawCircle(
                color = Color.Blue,
                radius = radioAnimado
            )
        }
    }
}
