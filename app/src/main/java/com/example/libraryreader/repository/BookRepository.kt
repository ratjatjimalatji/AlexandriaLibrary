package com.example.libraryreader.repository


import com.example.libraryreader.data.DataOrException
import com.example.libraryreader.data.Resource
import com.example.libraryreader.model.Item
import com.example.libraryreader.network.BooksApi
import jakarta.inject.Inject

class BookRepository @Inject constructor(private val api: BooksApi) {

    suspend fun getBooks(searchQuery: String): Resource<List<Item>> {
        return try {
            Resource.Loading(data = true)
            val itemList = api.getAllBooks(searchQuery).items
            if (itemList.isNotEmpty()) Resource.Loading(data = false)
            Resource.Success(data = itemList)
        } catch (e: Exception) {
            Resource.Error(message = e.message.toString())
        }
    }


    suspend fun getBookInfo(bookId: String): Resource<Item> {
        val response = try {
            Resource.Loading(data = true)
            api.getBookInfo(bookId)

        } catch (e: Exception) {
            return Resource.Error(message = "An error occurred ${e.message.toString()}")
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }
}
//
//    private val dataOrException = DataOrException<List<Item>, Boolean, Exception>()
//
//    private val bookInfoDataOrException = DataOrException<Item, Boolean, Exception>()
//
//    suspend fun getBooks(searchQuery: String): DataOrException<List<Item>,
//            Boolean,
//            Exception> {
//        try{
//            dataOrException.loading = true
//            dataOrException.data    = api.getAllBooks(searchQuery).items
//            if(dataOrException.data!!.isNotEmpty()) dataOrException.loading = false
//        }catch(e: Exception){
//            dataOrException.e = e
//        }
//
//        return dataOrException
//
//    }
//
//    suspend fun getBookInfo(bookId:String): DataOrException<Item, Boolean, Exception>{
//        val response = try{
//            bookInfoDataOrException.loading = true
//            bookInfoDataOrException.data = api.getBookInfo(bookId = bookId)
//            if(bookInfoDataOrException.data.toString().isNotEmpty()) bookInfoDataOrException.loading = false
//            else{}
//        }catch(e: Exception){
//            bookInfoDataOrException.e = e
//        }
//        return bookInfoDataOrException
//    }

