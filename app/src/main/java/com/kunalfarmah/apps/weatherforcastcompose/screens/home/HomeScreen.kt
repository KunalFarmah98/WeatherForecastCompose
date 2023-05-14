package com.kunalfarmah.apps.weatherforcastcompose.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kunalfarmah.apps.weatherforcastcompose.viewmodel.WeatherViewModel


@Composable
fun HomeScreen(navController: NavController) {
    val weatherViewModel: WeatherViewModel  = hiltViewModel()
    weatherViewModel.getCurrentForecast("delhi")
    weatherViewModel.getDailyForecast("delhi")
    MainScaffold(weatherViewModel = weatherViewModel)
}

@Composable
fun MainScaffold(weatherViewModel: WeatherViewModel) {

    val dailyDataFlow = weatherViewModel.dailyData.collectAsState().value

    val currentDataFlow = weatherViewModel.currentData.collectAsState().value

    Column(modifier = Modifier.fillMaxSize()) {

        if(dailyDataFlow.loading==true)
            Text(text = "loading daily forecast as flow")
        else {
            var weather = ""
            dailyDataFlow.data?.list?.forEach {
                weather+=it.weather.toString()
            }
            Text(text = weather)
        }
        Spacer(modifier = Modifier.height(10.dp))
        if(currentDataFlow.loading==true)
            Text(text = "loading current forecast as flow")
        else
            Text(text = currentDataFlow.data.toString())
    }




}