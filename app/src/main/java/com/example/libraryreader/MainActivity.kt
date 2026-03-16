package com.example.libraryreader

import android.R.attr.id
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

                val db = FirebaseFirestore.getInstance()

                val auth = FirebaseAuth.getInstance()

                val user:MutableMap<String, Any> = HashMap()
                user["firstname"] = "Joe"
                user["lastName"] = "Shmoe"

                db.collection("users")
                    .add(user)
                    .addOnSuccessListener {
Log.d("Firebase", "onCreate: ${it.id}}")
                }.addOnFailureListener {
Log.d("Firebase", "onCreate: $it")
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    LibraryReaderTheme {
        Greeting("Android")
    }
}