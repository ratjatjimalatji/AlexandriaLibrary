package com.example.libraryreader.screens.details

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.libraryreader.components.ReaderAppBar
import com.example.libraryreader.data.Resource
import com.example.libraryreader.model.Item
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Details(navController: NavController, bookId: String, viewModel: DetailsViewModel) {
    Scaffold(topBar = {
        ReaderAppBar(
            title = "Book details $bookId",
            icon = Icons.Default.ArrowBack,
            showIcon = false ,
            navController = navController){
            navController.popBackStack()
        }

    }) {
        Surface(modifier = Modifier.padding(30.dp)
        .fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(top = 50.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                val bookInfo = produceState<Resource<Item>>(Resource.Loading()) {
                    value = viewModel.getBookInfo(bookId)
                }.value

                if (bookInfo.data == null) {
                    Column() {
                        Text(text = "loading...")
                        LinearProgressIndicator(gapSize = 10.dp)
                    }
                } else {
                    ShowBookDetails(bookInfo, navController)

                }
            }
        }}}

@Composable
fun ShowBookDetails(bookInfo: Resource<Item>, navController: NavController) {
    val bookData = bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id

    Card(modifier = Modifier.padding(34.dp),
        shape = CircleShape)
        { Image(modifier = Modifier.height(100.dp)
            .width(100.dp),
            painter = rememberImagePainter(data = bookData?.imageLinks?.smallThumbnail), contentDescription = "book image")}
    Text(text = "Book Details Screen: ${bookData?.title}")
    Text(text = "Authors: ${bookData?.authors}")
    Text(text = "Categories: [${bookData?.categories}]")
    Text(text = "Published date: ${bookData?.publishedDate}")
    Row(){
        Button(onClick = {}){Text(text = "save")}
        Button(onClick = {navController.navigateUp()}){Text(text = "cancel")}
    }
}

