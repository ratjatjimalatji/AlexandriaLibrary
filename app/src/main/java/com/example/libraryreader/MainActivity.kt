package com.example.libraryreader

import android.R.attr.id
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraryreader.navigation.ReaderNavigation
import com.example.libraryreader.ui.theme.LibraryReaderTheme
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint // MainActivity will receive all dependency injections
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LibraryReaderTheme {

                ReaderApp()
                //Adding a user to firebase collection
//                val db = FirebaseFirestore.getInstance()
//
//                val auth = FirebaseAuth.getInstance()
//
//                val user:MutableMap<String, Any> = HashMap()
//                user["firstname"] = "Joe"
//                user["lastName"] = "Shmoe"
//
//                db.collection("users")
//                    .add(user)
//                    .addOnSuccessListener {
//Log.d("Firebase", "onCreate: ${it.id}}")
//                }.addOnFailureListener {
//Log.d("Firebase", "onCreate: $it")
//                }

            }
        }
    }
}

@Composable
fun ReaderApp(){
    Surface(modifier = Modifier.fillMaxSize()
        ,
        color = MaterialTheme.colorScheme.background) {
Column(verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally) {
    ReaderNavigation()}
    }
}

