package com.example.libraryreader.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.libraryreader.screens.ReaderSplashScreen
import com.example.libraryreader.screens.home.Home
import com.example.libraryreader.screens.login.Login
import com.example.libraryreader.screens.search.Search
import com.example.libraryreader.screens.stats.Stats
import com.example.libraryreader.screens.update.Update

@Composable
fun ReaderNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController,
        startDestination = ReaderScreens.SplashScreen.name // The application opens the splash screen upon app start
    ){


        composable(ReaderScreens.SplashScreen.name) {
            ReaderSplashScreen(navController = navController)
        }

        composable(ReaderScreens.HomeScreen.name) {
            Home(navController = navController)
        }

        composable(ReaderScreens.LoginScreen.name) {
            Login(navController = navController)
        }

        composable(ReaderScreens.SearchScreen.name) {
            Search(navController = navController)
        }

        composable(ReaderScreens.UpdateScreen.name) {
            Update(navController = navController)
        }

        composable(ReaderScreens.StatsScreen.name) {
            Stats(navController = navController)
        }

}}