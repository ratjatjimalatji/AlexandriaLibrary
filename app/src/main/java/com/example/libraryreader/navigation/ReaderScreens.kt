package com.example.libraryreader.navigation

enum class ReaderScreens {
    //Screens in the application
    SplashScreen,
    LoginScreen,
    CreateAccountScreen,
    HomeScreen,
    SearchScreen,
    DetailsScreen,
    StatsScreen,
    UpdateScreen;

    companion object {
        fun fromRoute(route:String?): ReaderScreens
        = when(route?.substringBefore("/")){
            SplashScreen.name -> SplashScreen
            LoginScreen.name -> LoginScreen
            HomeScreen.name -> HomeScreen
            SearchScreen.name -> SearchScreen
            DetailsScreen.name -> DetailsScreen
            StatsScreen.name -> StatsScreen
            UpdateScreen.name -> UpdateScreen
            null -> HomeScreen
            else -> throw java.lang.IllegalArgumentException("Route $route is not recognized")
        }
    }
}