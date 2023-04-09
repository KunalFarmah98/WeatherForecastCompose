package com.kunalfarmah.apps.weatherforcastcompose.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.kunalfarmah.apps.weatherforcastcompose.screens.home.HomeScreen
import com.kunalfarmah.apps.weatherforcastcompose.screens.splash.SplashScreen

@Composable
fun WeatherNavigation() {
    // creates a stateful nav controller
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name){
        composable(WeatherScreens.SplashScreen.name){
            SplashScreen(navController)
        }
        composable(WeatherScreens.HomeScreen.name){
            HomeScreen(navController)
        }
    }
}