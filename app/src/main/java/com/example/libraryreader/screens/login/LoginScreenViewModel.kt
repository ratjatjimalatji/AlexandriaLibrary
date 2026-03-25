package com.example.libraryreader.screens.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

class LoginScreenViewModel : ViewModel() {
    //val loadingState = MutableStateFlow(LoadingState.IDLE)
    private val auth: FirebaseAuth = Firebase.auth

    //private variable will be used internally in the class
    private val _loading = MutableLiveData(false)

    // public variable will be used outside the class
    val loading: LiveData<Boolean> = _loading


    fun signInWithEmailAndPassword(email: String, password: String, navigateToHomeScreen: () -> Unit) =
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            navigateToHomeScreen()
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

    //User registers and is added to the users collection
        fun createUser(displayName: String?) {
            val userId = auth.currentUser?.uid ?: return //auth has the refence of the logged in user

        val user = hashMapOf(
            "user_id" to userId,
            "display_name" to displayName.toString()
        )

        // Use .document(userId).set() instead of .add()
        FirebaseFirestore.getInstance().collection("users")
            .document(userId)
            .set(user)
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error creating user document", e)
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

                        //display name is usually name@gmail.com
                        //@ is the delimiter
                        //name = [0], @gmail.com = [1]
                        val displayName = task.result?.user?.email?.split('@')?.get(0)
                        createUser(displayName)

                        goToHomeScreen()
                    } else {
                        // FIX: Log the exception directly to avoid IllegalStateException
                        Log.e("Firebase", "Auth Failed", task.exception)
                    }
                    _loading.value = false
                }

        }

    }
}



