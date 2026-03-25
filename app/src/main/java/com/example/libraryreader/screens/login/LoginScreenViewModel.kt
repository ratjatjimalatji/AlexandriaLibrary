package com.example.libraryreader.screens.login

import android.R.id.message
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {
    //val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth

    //private variable will be used internally in the class
    private val _loading = MutableLiveData(false)

    // public variable will be used outside the class
    val loading: LiveData<Boolean> = _loading


    fun signInWithEmailAndPassword(email: String, password: String, home: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            Log.d(
                                "Firebase",
                                "signInWithEmailAndPassword: Login SUCCESS ${task.result.toString()}"
                            )
                            //TODO("Take user to home page")
                            home()
                        } else {
                            Log.d(
                                "Firebase",
                                "signInWithEmailAndPassword: Login FAILED ${task.result.toString()}"
                            )
                        }
                    }
            } catch (ex: Exception) {

            }
        }


    fun createUserWithEmailAndPassword(
        email: String,
        password: String,
        goToHomeScreen: () -> Unit
    ) {
        if (_loading.value == false) { //isLoading is initialised to false so this condition will always be true
            _loading.value = true //initiate loading process
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        goToHomeScreen()
                    } else {
                        Log.d(
                            "Firebase",
                            "createUserWithEmailAndPassword: ${task.result.toString()}"
                        )
                    }
                    _loading.value = false
                }

        }

    }
}



