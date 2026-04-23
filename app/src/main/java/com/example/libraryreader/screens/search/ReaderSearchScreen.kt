package com.example.libraryreader.screens.search

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.BottomSheetDefaults.Elevation
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.libraryreader.components.InputField
import com.example.libraryreader.components.ReaderAppBar
import com.example.libraryreader.model.Item
import com.example.libraryreader.navigation.ReaderScreens

@Composable
fun Search(navController: NavController, viewModel: BookSearchViewModel = hiltViewModel()) {


    Scaffold(
        topBar = {
            ReaderAppBar(
                icon = Icons.AutoMirrored.Filled.ArrowBack,
                showIcon = false,
                title = "Search books",
                navController = navController
            ) {

                //PopBackStack - Navigate to previous screen when icon back arrow
                navController.popBackStack()
            }
        },
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
                    ) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally) {
                SearchForm(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    hint = "Search book by title",
                    viewModel = viewModel
                ) { query ->
                    viewModel.searchBooks(query)
                    Log.d("TAG", "SearchScreen: $query")
                }

                BookList(navController = navController, viewModel)
            }
        }
    }
}

@Composable
fun BookList(navController: NavController,
             viewModel: BookSearchViewModel = hiltViewModel()){

val listOfBooks = viewModel.list
if(viewModel.isLoading){
    Column(){
        CircularProgressIndicator()
        Text(text="loading")
    }

}else{
    LazyColumn(modifier = Modifier.fillMaxWidth(0.9f)) {
        items(listOfBooks) { book ->
            BookRow(
                navController, book)
        }
    }
}
}

//Results from search screen
@Composable
fun BookRow(
    navController: NavController,
    book: Item
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable{
            navController.navigate(ReaderScreens.DetailsScreen.name +"/${book.id}")
        }
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 2.dp
    ) {
        Row(modifier = Modifier.padding(12.dp)) {

            val imageUrl: String = if(book.volumeInfo.imageLinks.smallThumbnail.isEmpty()==true){
            ""
            }
            else
            {
                book.volumeInfo.imageLinks.smallThumbnail
    }
            // Book Cover Placeholder
            Image(
                painter = rememberImagePainter(data = imageUrl),
                contentDescription = "Book image",
                modifier = Modifier
                    .width(80.dp)
                    .height(120.dp)
                    .clip(RoundedCornerShape(4.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text = book.volumeInfo.title ?: "No Title",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "Author: ${book.volumeInfo.authors}",
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Published by  ${book.volumeInfo.publisher}",
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Date: ${book.volumeInfo.publishedDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
                // Joining list of categories into a single string
                Text(
                    text = book.volumeInfo.categories.joinToString(", "),
                    style = MaterialTheme.typography.labelSmall,
                    color = Color.Blue
                )
                Text(
                        text = "Description: ${book.volumeInfo.description}",
                    maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
@Composable
fun SearchForm(modifier: Modifier,
               viewModel: BookSearchViewModel,
               loading : Boolean = false,
               hint: String = "Search",
               onSearch: (String) -> Unit = {}){
    Column() {
        val searchQueryState = rememberSaveable { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember (searchQueryState){
            searchQueryState.value.trim().isNotEmpty()
        }

        InputField(
            modifier = Modifier.fillMaxWidth(),
            valueState = searchQueryState,
            labelId = hint,
            enabled = true,
            isSingleLine = true,
            onAction = KeyboardActions{
                if (! valid) return@KeyboardActions
        onSearch(searchQueryState.value.trim())
        searchQueryState.value = ""
        keyboardController?.hide()

            }
        )
    }
}
