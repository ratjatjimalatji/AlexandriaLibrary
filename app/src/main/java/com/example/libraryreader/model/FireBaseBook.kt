package com.example.libraryreader.model

import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.PropertyName
import java.security.Timestamp

//Firebase Object attributes use camel casing user_id NOT userId, use getters and setters to prepare attributes to camel case so they save properly
data class FireBaseBook(
    @Exclude    var id: String?,
    var title: String,
    var authors: String,
    var notes: String,

    @get:PropertyName("book_photo_url")
    @set:PropertyName("book_photo_url")
    var photoUrl: String,

    @get:PropertyName("book_categories")
    @set:PropertyName("book_categories")
    var categories: List<String>,

    @get:PropertyName("book_published_date")
    @set:PropertyName("book_published_date")
    var publishedDate: String,

    @get:PropertyName("book_page_count")
    @set:PropertyName("book_page_count")
    var pageCount: Int,

    var rating: Double? = null,

    @get:PropertyName("book_started_reading")
    @set:PropertyName("book_started_reading")
    var startedReading: Timestamp? = null,

    @get:PropertyName("book_finished_reading")
    @set:PropertyName("book_finished_reading")
    var finishedReading: Timestamp? = null,

    @get:PropertyName("book_user_id")
    @set:PropertyName("book_user_id")
    var userId: String? = null,

    @get:PropertyName("book_google_book_id")
    @set:PropertyName("book_google_book_id")
    var googleBookId: String? = null
)
