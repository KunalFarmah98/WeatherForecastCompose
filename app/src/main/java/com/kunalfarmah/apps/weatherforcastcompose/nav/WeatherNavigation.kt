package com.kunalfarmah.apps.weatherforcastcompose.nav

import androidx.compose.runtime.Composable
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kunalfarmah.apps.weatherforcastcompose.screens.dropdown.AboutScreen
import com.kunalfarmah.apps.weatherforcastcompose.screens.dropdown.FavoriteScreen
import com.kunalfarmah.apps.weatherforcastcompose.screens.dropdown.SettingsScreen
import com.kunalfarmah.apps.weatherforcastcompose.screens.home.HomeScreen
import com.kunalfarmah.apps.weatherforcastcompose.screens.search.SearchScreen
import com.kunalfarmah.apps.weatherforcastcompose.screens.splash.SplashScreen

@Composable
fun WeatherNavigation() {
    // creates a stateful nav controller
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = WeatherScreens.SplashScreen.name){
        composable(WeatherScreens.SplashScreen.name){
            SplashScreen(navController)
        }
        // homescreen will have dynamic city
        val homeRoute = WeatherScreens.HomeScreen.name
        composable("$homeRoute/{city}",
        arguments = listOf(
            navArgument(name = "city") {
                type = NavType.StringType
            }
        )
        ){navBack ->
            navBack.arguments?.getString("city").let{
                HomeScreen(navController, it)
            }
        }
        composable(WeatherScreens.SearchScreen.name){
            SearchScreen(navController)
        }
        composable(WeatherScreens.AboutScreen.name){
            AboutScreen(navController)
        }
        composable(WeatherScreens.FavouriteScreen.name){
            FavoriteScreen(navController)
        }
        composable(WeatherScreens.SettingsScreen.name){
            SettingsScreen(navController)
        }
    }
}