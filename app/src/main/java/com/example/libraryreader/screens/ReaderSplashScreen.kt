package com.example.libraryreader.screens

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Preview
@Composable
fun ReaderSplashScreen(navController: NavController = NavController(context = LocalContext.current)) {


    Surface(modifier = Modifier
        .padding(15.dp)
        .size(330.dp),
        shape = CircleShape,
        color = Color.Blue,
        border = BorderStroke(width = 4.dp,
            color = Color.LightGray)
    ) {
        Column(modifier = Modifier.padding(1.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text="Alexandria lib",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White)
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