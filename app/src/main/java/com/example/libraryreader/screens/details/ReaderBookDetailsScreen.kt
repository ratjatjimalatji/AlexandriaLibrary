package com.example.libraryreader.screens.details

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.libraryreader.components.ReaderAppBar
import com.example.libraryreader.components.RoundedButton
import com.example.libraryreader.data.Resource
import com.example.libraryreader.model.FireBaseBook
import com.example.libraryreader.model.Item
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import androidx.compose.ui.platform.LocalResources
import coil.compose.rememberAsyncImagePainter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Details(navController: NavController, bookId: String, viewModel: DetailsViewModel) {
    Scaffold(
        topBar = {
        ReaderAppBar(
            title = "Book details",
            icon = Icons.AutoMirrored.Filled.ArrowBack,
            showIcon = false ,
            navController = navController){
            navController.popBackStack()
        }

    }) {
        Surface(modifier = Modifier
            .padding(30.dp)
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
        { Image(modifier = Modifier
            .height(100.dp)
            .width(100.dp),
            painter = rememberAsyncImagePainter(model = bookData?.imageLinks?.smallThumbnail), contentDescription = "book image")}
    Text(text = "Book Details Screen: ${bookData?.title}")
    Text(text = "Authors: ${bookData?.authors}")
    Text(text = "Categories: [${bookData?.categories}]", maxLines = 3, style = MaterialTheme.typography.titleSmall, overflow = TextOverflow.Ellipsis)
    Text(text = "Published date: ${bookData?.publishedDate}")

    //Removes html tags from description
    val cleanDescription = HtmlCompat.fromHtml(bookData!!.description,
        HtmlCompat.FROM_HTML_MODE_LEGACY).toString()
    val localDims = LocalResources.current.displayMetrics


Surface(modifier = Modifier
    .height(localDims.heightPixels.dp.times(0.09f))
    .padding(4.dp)
, shape = RectangleShape,
    border = BorderStroke(2.dp, Color.LightGray)
) {
    LazyColumn(modifier = Modifier.padding(3.dp)) {
        item {
            Text(text = "Details: ${cleanDescription}")
        }
    }
}
    Row(Modifier
        .fillMaxWidth()
        .padding(top = 20.dp), horizontalArrangement = Arrangement.SpaceAround){
        RoundedButton(label = "Save"){
            //save this book to the firestore database
            val book = FireBaseBook(
                title = bookData.title,
                authors = bookData.authors.toString(),
                description = bookData.description,
                categories = bookData.categories.toString(),
                notes = "",
                photoUrl = bookData.imageLinks.thumbnail,
                publishedDate = bookData.publishedDate,
                pageCount = bookData.pageCount.toString(),
                rating = 0.0,
                googleBookId = googleBookId,
                userId = FirebaseAuth.getInstance().currentUser?.uid.toString())

            saveToFirebase(book, navController = navController)

        }
        RoundedButton(label = "Cancel", onPress = {navController.navigateUp()} )
    }
}


fun saveToFirebase(book: FireBaseBook, navController: NavController) {
    val db = FirebaseFirestore.getInstance()
    val dbCollection = db.collection("books")

    if (book.toString().isNotEmpty()){
        dbCollection.add(book)
            .addOnSuccessListener { documentRef ->
                val docId = documentRef.id
                dbCollection.document(docId)
                    .update(hashMapOf("id" to docId) as Map<String, Any>)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            navController.popBackStack()
                        }
                    }.addOnFailureListener {
                        Log.w("Error", "SaveToFirebase:  Error updating doc", it)
                    }
            }
    }else {
    }
}