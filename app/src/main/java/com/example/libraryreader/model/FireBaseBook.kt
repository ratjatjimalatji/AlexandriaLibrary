package com.example.libraryreader.model

data class FireBaseBook(
    val id: String?,
    val title: String,
    val authors: String,
    val notes: String,
    val photoUrl: String,
    val categories: List<String>,
    val publishedDate: String,
    val pageCount: Int,
)
