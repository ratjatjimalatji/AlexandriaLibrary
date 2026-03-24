package com.example.libraryreader.screens.login



data class LoadingState(val status: Status, val message: String? = null) {
    /*A companion object in Kotlin is a special singleton object declared inside a
    class using the companion keyword. It allows you to define members
    (functions and properties) that are associated with the class itself,
    rather than instances of the class.*/
    companion object{
    val SUCCESS = LoadingState(Status.SUCCESS)
    val FAILED = LoadingState(Status.FAILED)
    val LOADING = LoadingState(Status.LOADING)
    val IDLE = LoadingState(Status.IDLE)
}

    enum class Status {
        SUCCESS,
        FAILED,
        LOADING,
        IDLE
    }
}