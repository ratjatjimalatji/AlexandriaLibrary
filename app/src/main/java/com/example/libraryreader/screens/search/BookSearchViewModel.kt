package com.example.libraryreader.screens.search


import android.util.Log
import android.util.Log.e
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryreader.data.DataOrException
import com.example.libraryreader.data.Resource
import com.example.libraryreader.model.Item
import com.example.libraryreader.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel

class BookSearchViewModel @Inject constructor(private val repository: BookRepository) :
    ViewModel() {

    var list: List<Item>  by mutableStateOf(listOf())
    var isLoading: Boolean by mutableStateOf(true)

    init {
        loadBooks()
    }

     fun loadBooks() {
        searchBooks("android")
    }

    fun searchBooks(query: String) {
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isEmpty()) {
                return@launch
            }
            try {
                when (val response = repository.getBooks(query)) {
                    is Resource.Success -> {
                        list = response.data!!
                        if(list.isNotEmpty()) isLoading =false
                    }

                    is Resource.Error -> {
                        isLoading = false
                        Log.d("Network", "searchBooks: Failed getting books")
                    }

                    else -> {
                        isLoading = false
                    }
                }
            } catch (e: Exception) {
                Log.d("Network", "searchBooks: ${e.message.toString()}")
            isLoading = false
            }
        }
    }


}