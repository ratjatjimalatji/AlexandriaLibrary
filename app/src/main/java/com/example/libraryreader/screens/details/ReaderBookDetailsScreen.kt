package com.example.libraryreader.screens.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.libraryreader.components.ReaderAppBar

@Composable
fun Details(navController: NavController, bookId: String) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Book details $bookId",
            icon = Icons.Default.ArrowBack,
            showIcon = false ,
            navController = navController){
            navController.popBackStack()
        }

    }) {
        Surface(modifier = Modifier.padding(3.dp)
        //.fillMaxSize()
        ) {
            Column(modifier = Modifier.padding(top=12.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                Text(text = "tetstetete")
                Text(text = "Book Details Screen: $bookId")
            }
        }
    }

}