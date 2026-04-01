package com.example.libraryreader.screens.search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.libraryreader.components.ReaderAppBar
import com.example.libraryreader.navigation.ReaderScreens

@Preview
@Composable
fun Search(navController: NavController = NavController(LocalContext.current)){
    Scaffold(
        topBar = {
            ReaderAppBar(icon = Icons.AutoMirrored.Filled.ArrowBack,showIcon = false, title = "Search books", navController = navController) {
            //PopBackStack - Navigate to previous screen when icon back arrow
                navController.popBackStack()
            }
                 },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

        }
    }
}

