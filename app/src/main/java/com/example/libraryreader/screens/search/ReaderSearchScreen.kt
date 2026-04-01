package com.example.libraryreader.screens.search

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.libraryreader.components.InputField
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
Column(modifier = Modifier.fillMaxWidth()){
    SearchForm(modifier = Modifier.fillMaxWidth().padding(16.dp), hint = "Search bok by title", onSearch = { })
}
        }
    }
}

@Composable
fun SearchForm(modifier: Modifier, loading : Boolean = false, hint: String = "Search", onSearch: (String) -> Unit = {}){
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
