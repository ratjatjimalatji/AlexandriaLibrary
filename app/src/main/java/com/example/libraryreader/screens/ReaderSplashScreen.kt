package com.example.libraryreader.screens

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.libraryreader.components.ReaderLogo
import com.example.libraryreader.navigation.ReaderScreens
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay


@Composable
fun ReaderSplashScreen(navController: NavController) {
val scale = remember {
    Animatable(0f)
}

    LaunchedEffect(key1 = true){
        scale.animateTo(targetValue = 0.9f,
            animationSpec = tween(durationMillis = 800,
                easing = {
                    OvershootInterpolator(2f)
                        .getInterpolation(it)
                })
        )
        delay(1000L) // 1 second delay

        // bypassing logging in if users is already logged in
        if (FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
        navController.navigate(ReaderScreens.LoginScreen.name)
    }else{
        navController.navigate(ReaderScreens.HomeScreen.name)
         }
    }
    Surface(modifier = Modifier
        .padding(15.dp)
        .size(330.dp)
        .scale(scale.value), // passing scale value to
        shape = CircleShape,
        color = Color.Blue,
        border = BorderStroke(width = 4.dp,
            color = Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(1.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {

            ReaderLogo(color = Color.White)

            Text(text=" The largest library of books",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.LightGray,
                textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(30.dp))
            Text(text="created by Ratjatji Malatji",
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
                )
        }
    }

}

