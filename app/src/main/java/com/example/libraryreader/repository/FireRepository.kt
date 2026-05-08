package com.example.libraryreader.repository

import com.example.libraryreader.data.DataOrException
import com.example.libraryreader.model.FireBaseBook
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireRepository @Inject constructor(
    private val queryBook: Query
) {
    suspend fun getAllBooksFromDatabase(): DataOrException<List<FireBaseBook>, Boolean, Exception> {
        val dataOrException = DataOrException<List<FireBaseBook>, Boolean, Exception>()

        try {
            dataOrException.loading = true
            dataOrException.data = queryBook.get().await().documents.map { documentSnapshot ->
                documentSnapshot.toObject(FireBaseBook::class.java)!!
            }
            if (!dataOrException.data.isNullOrEmpty()) dataOrException.loading = false


        } catch (exception: FirebaseFirestoreException) {
            dataOrException.e = exception
        }
        return dataOrException

    }
}