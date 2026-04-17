package com.example.libraryreader.screens.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import com.example.libraryreader.components.ReaderAppBar
import com.example.libraryreader.data.Resource
import com.example.libraryreader.model.Item

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
            Column(modifier = Modifier.padding(top=50.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                val bookInfo = produceState<Resource<Item>>(Resource.Loading()){
                    value = viewModel.getBookInfo(bookId)
                }.value

                if(bookInfo.data == null){
                    LinearProgressIndicator()
                }else{
                Text(text = "Book Details Screen: ${bookInfo.data.volumeInfo.title}")
            }
        }
    }

}
}