package com.kunalfarmah.apps.weatherforcastcompose.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.kunalfarmah.apps.weatherforcastcompose.data.DataOrException
import com.kunalfarmah.apps.weatherforcastcompose.model.DailyForecast
import com.kunalfarmah.apps.weatherforcastcompose.viewmodel.WeatherViewModel
import com.kunalfarmah.apps.weatherforcastcompose.widgets.WeatherAppBar


@Composable
fun HomeScreen(navController: NavController) {
    val weatherViewModel: WeatherViewModel = hiltViewModel()
    weatherViewModel.getCurrentForecast("delhi")
    weatherViewModel.getDailyForecast("delhi")
    val currentData = weatherViewModel.currentData.collectAsState()
    val dailyData = weatherViewModel.dailyData.collectAsState().value
    MainScaffold(dailyData, navController)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(dailyData: DataOrException<DailyForecast, Boolean, Exception>, navController: NavController) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "${dailyData.data?.city?.name}, ${dailyData.data?.city?.country ?: ""}",
            navController = navController,
            elevation = 5.dp
        ) {
            Log.d("Home", "MainScaffold: button clicked")
        }
    }) {
        MainContent(dailyData, paddingValues = it)
    }
}

@Composable
fun MainContent(dailyData:DataOrException<DailyForecast, Boolean, Exception>, paddingValues: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = paddingValues.calculateTopPadding(),
                start = 4.dp,
                end = 4.dp,
                bottom = paddingValues.calculateBottomPadding()
            ),
        verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Nov 29",
            style = MaterialTheme.typography.labelLarge,
            color = Color.Black,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(6.dp)
        )

        Surface(
            modifier = Modifier
                .padding(4.dp)
                .size(200.dp),
            shape = CircleShape,
            color = Color(0xFFFFC400)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "56", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.ExtraBold)
                Text(text = "Snow", fontStyle = FontStyle.Italic)

            }
        }
    }
}