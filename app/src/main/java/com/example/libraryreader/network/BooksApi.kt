package com.example.libraryreader.network

import com.example.libraryreader.model.Book
import com.example.libraryreader.model.Item
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface BooksApi {
    //BASE URL  is = "https://www.googleapis.com/books/v1/ .
    // Volumes must always be appended that why @gets is added

    //    https://www.googleapis.com/books/v1/volumes?q=andriod
    @GET("volumes") //^
    suspend fun getAllBooks(@Query("q") query: String): Book// ^

    //find a specific book by passing ID into URL, books details will appear
    //https://www.googleapis.com/books/v1/volumes/m4uP0ujpXtUC
    @GET("volumes/{bookId}")
    suspend fun getBookInfo(@Path("bookId") bookId: String): Item
}