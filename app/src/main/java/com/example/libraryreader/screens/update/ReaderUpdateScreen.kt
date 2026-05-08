package com.example.libraryreader.screens.update

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.libraryreader.components.ReaderAppBar
import com.example.libraryreader.data.DataOrException
import com.example.libraryreader.model.FireBaseBook
import com.example.libraryreader.screens.home.HomeScreenViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun Update(navController: NavHostController, bookItemId: String, viewModel: HomeScreenViewModel = hiltViewModel()){
    Scaffold(
        topBar = {
            ReaderAppBar(
                title = "Update Book",
                showIcon = false,
                navController = navController
            ){
                navController.popBackStack()
            }
        }
    ) {

        val bookInfo = produceState<DataOrException<List<FireBaseBook>,
                Boolean,
                Exception>>(initialValue = DataOrException(data = emptyList(),
            true, Exception(""))){
            value = viewModel.data.value
        }.value

        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(3.dp)) {
            Column(modifier = Modifier.padding(top = 3.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = CenterHorizontally)
            {
                Log.d("Info", "Book Update Screen: ${viewModel.data.value.data.toString()}")
            if (bookInfo.loading == true){
                LinearProgressIndicator()
                bookInfo.loading = false
            }
                else{
                Text(text=viewModel.data.value.data?.get(0)?.title.toString())
            }
            }
    }
}
}

