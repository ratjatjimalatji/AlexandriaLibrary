package com.example.libraryreader.screens.login

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.libraryreader.components.ReaderLogo
import com.example.libraryreader.components.LoginSignUpUserForm
import com.example.libraryreader.navigation.ReaderScreens

@Composable
fun Login(navController: NavController,
          viewModel: LoginScreenViewModel = viewModel() // from loginScreenViewModel

) {

    val showLoginForm = rememberSaveable { mutableStateOf(true) }
    val pageText = if (showLoginForm.value) "Login page" else "Register page"

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top=40.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ReaderLogo(color = Color.Blue)
            Text(
                pageText, modifier = Modifier
                    .padding(start = 5.dp),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.secondary)

            if (showLoginForm.value) {
                LoginSignUpUserForm(loading = false, registerScreen = false) { email, pwd ->
                    //Navigate to HomeScreen after a successful login
                    viewModel.signInWithEmailAndPassword(email,pwd){
                        navController.navigate(ReaderScreens.HomeScreen.name)
                    }
}
            } else {


                LoginSignUpUserForm(loading = false, registerScreen = true) { email, password ->
                viewModel.createUserWithEmailAndPassword(email,password){
                    navController.navigate(ReaderScreens.HomeScreen.name)
                }
            }
            }
            Spacer(modifier = Modifier.height(50.dp))
            Row(
                modifier = Modifier.padding(15.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val text = if (showLoginForm.value) "Don't have an account? Create one" else "Have an account? Login"

                //Text(text = "New User?")
                Text(
                    text, modifier = Modifier
                        .clickable {
                            showLoginForm.value = !showLoginForm.value
                        }
                        .padding(start = 5.dp),
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}


