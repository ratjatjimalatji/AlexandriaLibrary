package com.example.libraryreader.screens.home


import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.libraryreader.components.ContentBelowTopAppBar
import com.example.libraryreader.components.FABContent
import com.example.libraryreader.components.HorizontalScrollableComponent
import com.example.libraryreader.components.ReaderAppBar
import com.example.libraryreader.model.FireBaseBook
import com.example.libraryreader.navigation.ReaderScreens

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Home(navController: NavController, viewModel: HomeScreenViewModel = hiltViewModel()) {
    Scaffold(
        topBar = {ReaderAppBar(null, "Alexandria Library", navController = navController)},
        floatingActionButton = {
            FABContent() {
                navController.navigate(ReaderScreens.SearchScreen.name)
            }
        }) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(modifier = Modifier
                .background(Color.White)
                .fillMaxSize()) {
            ContentBelowTopAppBar(navController, viewModel)
        }
    }

}}

@Composable
fun ReadingRightNowArea(books: List<FireBaseBook>, navController: NavController) {

    HorizontalScrollableComponent(books) {
        navController.navigate(ReaderScreens.UpdateScreen.name +"/$it")
    }
}


